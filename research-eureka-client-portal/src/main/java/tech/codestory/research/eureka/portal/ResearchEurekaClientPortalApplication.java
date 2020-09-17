package tech.codestory.research.eureka.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 测试 Eureka 注册服务的消费者，会调用注册到 eureka 的服务
 *
 * @author junyongliao
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ResearchEurekaClientPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchEurekaClientPortalApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
