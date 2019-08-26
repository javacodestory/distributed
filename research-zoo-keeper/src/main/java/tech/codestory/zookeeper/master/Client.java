package tech.codestory.zookeeper.master;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import tech.codestory.zookeeper.callback.ZooKeeperMaster;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class Client implements Watcher {
    ZooKeeper zk;
    String hostPort;

    Random random = new SecureRandom();
    String serverId = Integer.toHexString(random.nextInt());

    public Client(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws Exception {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    String queueCommand(String command) throws Exception {
        while (true) {
            String name = null;
            try {
                name = zk.create("/tasks/task-" + serverId, command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
                return name;
            } catch (KeeperException.NodeExistsException e) {
                throw new Exception(serverId + " already appears to be running");
            } catch (KeeperException.ConnectionLossException e) {
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("event {}", event);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost:2181");
        client.startZK();
        String name = client.queueCommand("command");
        log.info("created : {}", name);
    }
}
