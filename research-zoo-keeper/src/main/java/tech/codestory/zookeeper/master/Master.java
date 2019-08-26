package tech.codestory.zookeeper.master;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 主从模式的Master
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class Master implements Watcher {
    ZooKeeper zk;
    String hostPort;

    Random random = new SecureRandom();
    String serverId = Integer.toHexString(random.nextInt());

    boolean isLeader = false;

    Master(String hostPort) {
        this.hostPort = hostPort;
    }

    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                    break;
                default:
                    isLeader = false;
            }
            log.info("I'm {}the leader", isLeader ? "" : "not ");
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
                    log.info("Parent {} Created", path);
                    break;
                case NODEEXISTS:
                    log.warn("Parent {} already registered. ", path);
                    break;
                default:
                    log.error("Something went wrong", KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    public void bootstrap() {
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    void createParent(String path, byte[] data) {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createParentCallback, data);
    }

    public boolean isLeader() {
        return isLeader;
    }

    void startZK() throws Exception {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    void stopZK() throws Exception {
        zk.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("watched event : {}", watchedEvent);
    }

    void checkMaster() {
        zk.getData("/master", false, masterCheckCallback, null);
    }

    void runForMaster() {
        zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                masterCreateCallback, null);
        isLeader = true;
    }

    public static void main(String[] args) throws Exception {
        Master master = new Master("localhost:2181");

        master.startZK();

        master.runForMaster();

        if (master.isLeader) {
            log.info("I'm the leader");
            Thread.sleep(10000);
        } else {
            log.info("Someone else is the leader");
        }

        master.stopZK();
    }
}