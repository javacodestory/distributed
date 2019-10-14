package tech.codestory.zookeeper.master;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import tech.codestory.zookeeper.ZooKeeperBase;
import tech.codestory.zookeeper.callback.ZooKeeperMaster;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class Client extends ZooKeeperBase {
    public Client(String address) throws IOException {
        super(address);
    }

    /**
     * 发布一个新任务
     *
     * @param command
     * @return
     * @throws Exception
     */
    String queueCommand(String command) throws Exception {
        while (true) {
            String name = null;
            try {
                name = getZooKeeper().create("/tasks/task-" + serverId, command.getBytes("UTF-8"),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                return name;
            } catch (KeeperException.NodeExistsException e) {
                throw new Exception(serverId + " already appears to be running");
            } catch (KeeperException.ConnectionLossException e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("zookeeper-host:2181");
        String name = client.queueCommand("command");
        log.info("created : {}", name);
    }
}
