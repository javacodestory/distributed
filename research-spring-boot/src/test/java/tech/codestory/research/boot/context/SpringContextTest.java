package tech.codestory.research.boot.context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * 测试Spring项目成功启动
 *
 * @author Javacodestory@gmail.com
 */
@SpringBootTest
@Slf4j
public class SpringContextTest {
    @Autowired
    ApplicationContext context;

    @Test
    void testContextLoads() {
        assert context != null;
        log.info("context = {}", context.getClass().getName());
    }
}
