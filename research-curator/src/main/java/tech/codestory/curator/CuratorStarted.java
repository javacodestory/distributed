package tech.codestory.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.SetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import java.util.concurrent.TimeUnit;

/**
 * 第一个 Curator 程序
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
public class CuratorStarted {
    public static void main(String[] args) throws Exception {
        String address = "localhost:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();

        CreateBuilder createBuilder = client.create();
        createBuilder.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE);
        createBuilder.withMode(CreateMode.EPHEMERAL);
        createBuilder.forPath("/curator", "Hello World".getBytes());

        TimeUnit.SECONDS.sleep(10);

        client.setData().forPath("/curator", "Hello World 01".getBytes());

        TimeUnit.SECONDS.sleep(10);

        client.close();
    }
}
