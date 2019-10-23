package tech.codestory.research.eureka.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试 Eureka 注册服务的消费者，会调用注册到 eureka 的服务
 * @author junyongliao
 */
@SpringBootApplication
public class ResearchEurekaClientPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchEurekaClientPortalApplication.class, args);
    }

}
