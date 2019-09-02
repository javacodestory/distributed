package tech.codestory.curator.recipes;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * DistributedLock 的客户端
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class DistributedLockClient extends Thread {
    CuratorFramework client;
    String lockPath;

    boolean executed = false;

    public DistributedLockClient(String hostPort, String lockPath) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(hostPort, retryPolicy);

        client.start();

        this.lockPath = lockPath;
    }

    @Override
    public void run() {
        long maxWait = 60;
        Random random = new SecureRandom();
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        try {
            if (lock.acquire(maxWait, TimeUnit.SECONDS)) {
                int minutes = random.nextInt(4) + 1;
                log.info("线程 {} 取到分布式锁, 假装工作 {} 秒", this.getId(), minutes);
                try {
                    Thread.sleep(minutes * 1000);
                    executed = true;
                } finally {
                    log.info("线程 {} 释放分布式锁\r\n", this.getId());
                    lock.release();
                }
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    public boolean isExecuted() {
        return executed;
    }
}
