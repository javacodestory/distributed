package tech.codestory.zookeeper.master;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import tech.codestory.zookeeper.TestBase;

/**
 * 测试竞争 Leader
 *
 * @author javacodestory@gmail.com
 * @date 2019/8/27
 */
public class RawMasterTest extends TestBase {
    class MasterThread extends Thread {
        RawMaster master;

        public MasterThread(RawMaster master) {
            this.master = master;
        }

        @Override
        public void run() {
            master.run();
        }

        public boolean isLeader() {
            return master.isLeader();
        }

        public void quit() {
            master.quit();
        }

        public void runForMaster() {
            master.runForMaster();
        }
    }

    @Test
    public void testMaster() throws IOException {
        Random random = new SecureRandom();
        int threadCount = 10;
        List<MasterThread> threadList = Lists.newArrayList();
        for (int i = 0; i < threadCount; i++) {
            MasterThread thread = new MasterThread(new RawMaster(address));
            thread.start();
            threadList.add(thread);
        }

        try {
            // 等待5-15秒
            Thread.sleep((random.nextInt(10) + 5) * 1000);
        } catch (InterruptedException e) {
        }

        while (!threadList.isEmpty()) {
            int leaderIndex = -1;
            for (int i = 0; i < threadList.size(); i++) {
                if (threadList.get(i).isLeader()) {
                    leaderIndex = i;
                    break;
                }
            }
            try {
                // 等待5-15秒
                Thread.sleep((random.nextInt(10) + 5) * 1000);
            } catch (InterruptedException e) {
            }

            if (leaderIndex >= 0) {
                // 让当前的Leader 退出，应该引发新一轮竞争
                threadList.get(leaderIndex).quit();
                threadList.remove(leaderIndex);
            }
        }
    }
}
