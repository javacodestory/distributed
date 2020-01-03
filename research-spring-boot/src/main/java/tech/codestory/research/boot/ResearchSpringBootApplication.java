package tech.codestory.research.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Research Spring Boot Demo Application
 *
 * @author liaojunyong
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class ResearchSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResearchSpringBootApplication.class, args);
    }
}
