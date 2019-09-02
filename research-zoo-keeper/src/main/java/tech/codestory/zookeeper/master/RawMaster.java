package tech.codestory.zookeeper.master;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import tech.codestory.zookeeper.ZooKeeperBase;

/**
 * @author junyongliao
 * @date 2019/8/29
 * @since 1.0.0
 */
@Slf4j
public class RawMaster extends ZooKeeperBase implements Runnable {
    public static final String PATH_MASTER = "/master";
    boolean isLeader = false;

    /** 系统退出的信号量 */
    Integer quitMutex = Integer.valueOf(-1);
    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                    log.info("masterCreateCallback : Master {} is the leader", serverId);
                    bootstrap();
                    break;
                default:
                    isLeader = false;
                    monitorLeaderLost();
            }
        }
    };
    AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case NONODE:
                    runForMaster();
                    return;
                case OK:
                    isLeader = new String(data, StandardCharsets.UTF_8).equals(serverId);
                    if (isLeader) {
                        log.info("masterCheckCallback : Master {} is the leader", serverId);
                        bootstrap();
                    } else {
                        monitorLeaderLost();
                    }
            }
        }
    };
    AsyncCallback.StatCallback monitorMasterLostCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    monitorLeaderLost();
                    break;
                case OK:
                    break;
            }
        }
    };
    Watcher masterNodeDeletedWatcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == Event.EventType.NodeDeleted
                    && PATH_MASTER.equals(event.getPath())) {
                log.info("{} : Master节点被删除，重新竞争Leader", serverId);
                runForMaster();
            }
        }
    };
    AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    break;
                case NODEEXISTS:
                    break;
                default:
                    log.error("Something went wrong",
                            KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    public RawMaster(String address) throws IOException {
        super(address);
    }

    /**
     * 退出系统
     */
    public void quit() {
        log.info("quit master instance {} , isLeader : {}", serverId, isLeader);
        if (isLeader) {
            // 当前是Leader，删除节点
            try {
                getZooKeeper().delete(PATH_MASTER, -1);
            } catch (Exception e) {
                log.error("Exception", e);
            }
        }
        synchronized (quitMutex) {
            quitMutex.notify();
        }
    }

    /**
     * 创建几个主要的根节点
     */
    public void bootstrap() {
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    void createParent(String path, byte[] data) {
        getZooKeeper().create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
                createParentCallback, data);
    }

    /**
     * 竞争 Leader
     */
    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    void runForMaster() {
        getZooKeeper().create(PATH_MASTER, serverId.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback,
                serverId.getBytes());
    }

    /**
     * 检查自己是否是 Leader
     */
    void checkMaster() {
        getZooKeeper().getData(PATH_MASTER, false, masterCheckCallback, null);
    }

    public boolean isLeader() {
        return isLeader;
    }

    /**
     * 监听Master节点
     */
    public void monitorLeaderLost() {
        log.info("{} 监控 Master 节点的变化", serverId);
        getZooKeeper().exists(PATH_MASTER, masterNodeDeletedWatcher, monitorMasterLostCallback,
                null);
    }

    @Override
    public void run() {
        // 竞争成为Leader
        runForMaster();

        // 死循环，直到收到通知退出系统
        synchronized (quitMutex) {
            try {
                quitMutex.wait();
            } catch (InterruptedException e) {
            }
        }
    }
}
