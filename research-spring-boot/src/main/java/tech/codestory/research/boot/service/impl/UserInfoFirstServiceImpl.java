package tech.codestory.research.boot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.codestory.research.boot.model.UserInfo;
import tech.codestory.research.boot.service.UserInfoFirstService;
import tech.codestory.research.boot.thread.InheritableRemoteUserContainer;
import tech.codestory.research.boot.thread.RemoteUserContainer;

/**
 * 没有添加其他注解的实现类
 *
 * @author javacodestory@gmail.com
 */
@Service
@Slf4j
public class UserInfoFirstServiceImpl implements UserInfoFirstService {
    /**
     * 获取一个用户信息
     *
     * @param account
     * @return
     */
    @Override
    public UserInfo getUserInfo(String account) {
        return null;
    }

    @Override
    //  @Async
    @Cacheable("remote.user")
    public String printRemoteUser() {
        simulateSlowService();
        log.info("remote user = {}", RemoteUserContainer.getRemoteUser());
        log.info("inheritable remote user = {}", InheritableRemoteUserContainer.getRemoteUser());
        return InheritableRemoteUserContainer.getRemoteUser();
    }

    /**
     * 慢三秒
     */
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
