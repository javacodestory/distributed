package tech.codestory.research.boot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import tech.codestory.research.boot.service.UserInfoFirstService;
import tech.codestory.research.boot.service.impl.UserInfoFirstServiceImpl;

/**
 * 测试UserInfoFirstService
 *
 * @author javacodestory@gmail.com
 */
@SpringBootTest
@Slf4j
public class UserInfoFirstServiceTest {
    @Autowired
    UserInfoFirstService firstService;
    @Autowired
    UserInfoFirstServiceImpl firstServiceImpl;
    @Autowired
    ApplicationContext context;

    @Test
    public void testServiceInstances() {
        log.info("firstService = {}", firstService);
        assert firstService != null;
        log.info("firstServiceImpl = {}", firstServiceImpl);
        assert firstServiceImpl != null;

        // 是同一个实例
        log.info("firstService 和 firstServiceImpl 是同一个Bean  = {}", firstService == firstServiceImpl);
        assert firstService == firstServiceImpl;
    }

    @Test
    public void testContextGetBean1() {
        // 通过 beanName 获取对象
        Object bean1 = context.getBean("userInfoFirstServiceImpl");
        log.info("bean1 = {}", bean1);
        assert bean1 != null;
        assert bean1 == firstService;
    }

    @Test
    public void testContextGetBean2() {
        // 通过 beanName 和 Class 获取对象
        UserInfoFirstService bean2 = context.getBean("userInfoFirstServiceImpl", UserInfoFirstService.class);
        log.info("bean2 = {}", bean2);
        assert bean2 != null;
        assert bean2 == firstService;
    }

    @Test
    public void testContextGetBean3() {
        // 通过 Class 获取对象
        UserInfoFirstService bean3 = context.getBean(UserInfoFirstService.class);
        log.info("bean3 = {}", bean3);
        assert bean3 != null;
        assert bean3 == firstService;
    }
}
