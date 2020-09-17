package tech.codestory.research.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Research Spring Boot Demo Application
 *
 * @author javacodestory@gmail.com
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
public class ResearchSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResearchSpringBootApplication.class, args);
    }
}
