package tech.codestory.curator.recipes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * 测试 Curator 提供的分布式锁
 *
 * @author javacodestory
 * @date 2019/8/26
 */
@Slf4j
public class DistributedLockClientTest {
    @Test
    public void testBlockingLock() {
        String hostPort = "zookeeper-host:2181";
        String lockPath = "/lock-" + System.currentTimeMillis();
        int threadCount = 5;
        DistributedLockClient[] clients = new DistributedLockClient[threadCount];
        for (int i = 0; i < threadCount; i++) {
            clients[i] = new DistributedLockClient(hostPort, lockPath);
        }
        for (int i = 0; i < threadCount; i++) {
            clients[i].start();
        }

        int executedCount = 0;

        for (int i = 0; i < threadCount; i++) {
            try {
                clients[i].join();
                if (clients[i].isExecuted()) {
                    executedCount++;
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
            }
        }

        assert executedCount == threadCount;
    }
}