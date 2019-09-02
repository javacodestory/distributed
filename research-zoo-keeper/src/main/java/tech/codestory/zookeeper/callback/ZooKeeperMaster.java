package tech.codestory.zookeeper.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.zip.ZipEntry;

/**
 * 熟悉使用异步消息
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class ZooKeeperMaster implements Watcher {
    /**
     * 服务器地址
     */
    private String address = "localhost:2181";

    ZooKeeper zk;

    public String serverId;

    public boolean isLeader;

    public ZooKeeperMaster(String address) {
        this.address = address;
        Random random = new SecureRandom();
        serverId = Long.toHexString(random.nextLong());
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(address, 15000, this);
    }

    void stopZK() throws InterruptedException {
        zk.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("WatchedEvent : {}", watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        ZooKeeperMasterThread thread1 = new ZooKeeperMasterThread();
        ZooKeeperMasterThread thread2 = new ZooKeeperMasterThread();
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    static class ZooKeeperMasterThread extends Thread {
        @Override
        public void run() {
            try {
                ZooKeeperMaster master = new ZooKeeperMaster("localhost:2181");
                master.startZK();
                master.runForMaster();
                if (master.isLeader) {
                    log.info("这儿需要等一会儿，异步线程才能收到消息");
                    Thread.sleep(10000);
                } else {
                    log.info("Someone else is the leader");
                }
                master.stopZK();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }

    static class ZooKeeperMasterCallback implements AsyncCallback.StringCallback {
        private ZooKeeperMaster master;

        public ZooKeeperMasterCallback(ZooKeeperMaster master) {
            this.master = master;
        }

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    master.checkMaster();
                    return;
                case OK:
                    master.isLeader = true;
                    break;
                default:
                    master.isLeader = false;
            }
            log.info("我{}是老大", master.isLeader ? "" : "不");
        }
    }

    public boolean checkMaster() {
        while (true) {
            try {
                Stat stat = new Stat();
                byte[] data = zk.getData("/master", false, stat);
                String readValue = new String(data, StandardCharsets.UTF_8);
                isLeader = readValue.equals(serverId);
                log.info("get node value {} compare with {}", readValue, serverId);
                return true;
            } catch (KeeperException.NoNodeException e) {
                return false;
            } catch (KeeperException e) {
            } catch (InterruptedException e) {
            }
        }
    }

    public void runForMaster() {
        while (true) {
            ZooKeeperMasterCallback masterCreateCallback = new ZooKeeperMasterCallback(this);
            try {
                log.info("create node with {}", serverId);
                zk.create("/master", serverId.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL, masterCreateCallback, null);
                isLeader = true;
                break;
            } catch (Exception e) {
                log.error("Exception", e);
            }
            if (checkMaster()) {
                break;
            }
        }
    }
}
