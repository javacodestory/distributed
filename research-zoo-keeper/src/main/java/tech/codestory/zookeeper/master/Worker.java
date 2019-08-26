package tech.codestory.zookeeper.master;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Worker
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class Worker implements Watcher {
    ZooKeeper zk;
    String hostPort;

    Random random = new SecureRandom();
    String serverId = Integer.toHexString(random.nextInt());

    String name;
    String status;

    public Worker(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws Exception {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("{} , {}", event, hostPort);
    }

    AsyncCallback.StringCallback createWorkerCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    log.info("Worker {} Registered successfully", serverId);
                    break;
                case NODEEXISTS:
                    log.warn("Worker {} already registered", serverId);
                    break;
                default:
                    log.error("Something went wrong", KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    AsyncCallback.StatCallback statusUpdateCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    updateStatus((String) ctx);
                    return;
            }
        }
    };

    void register() {
        zk.create("/workers/worker-" + serverId, "Idle".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL
                , createWorkerCallback, null);
    }

    synchronized void updateStatus(String status) {
        if (status.equals(this.status)) {
            zk.setData("/workers/" + name, status.getBytes(), -1, statusUpdateCallback, status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
        updateStatus(status);
    }

    public static void main(String[] args) throws Exception {
        Worker worker = new Worker("localhost:2181");
        worker.startZK();
        worker.register();
        Thread.sleep(30000);
    }
}
