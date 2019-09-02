package tech.codestory.zookeeper.master;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import lombok.extern.slf4j.Slf4j;

/**
 * 主从模式的Master
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/26
 */
@Slf4j
public class Master extends RawMaster {
    String status;

    Master(String address) throws IOException {
        super(address);
    }

    AsyncCallback.StatCallback monitorTasksCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    monitorTasks();
                    break;
                case OK:
                    getZooKeeper().getChildren("/tasks", false, getTasksCallback, null);
                    break;
            }
        }
    };

    AsyncCallback.ChildrenCallback getTasksCallback = new AsyncCallback.ChildrenCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            if (children != null && !children.isEmpty()) {
                // 分配任务
                log.info("当前有 {} 个任务需要分配", children.size());
            }
            monitorTasks();
        }
    };

    void monitorTasks() {
        if (isLeader) {
            // 监控任务队列
            getZooKeeper().exists("/tasks", true, monitorTasksCallback, null);
        }
    }

}
