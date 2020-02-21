package tech.codestory.research.boot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.codestory.research.boot.service.UserInfoFirstService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试UserInfoService
 *
 * @author javacodestory@gmail.com
 */
@SpringBootTest
@Slf4j
class UserInfoServiceTest {
    @Autowired
    UserInfoService userService;

    @Test
    public void testServiceInstances() {
        log.info("userService = {}", userService);
        assert userService != null;
    }
}
