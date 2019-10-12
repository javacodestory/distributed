package tech.codestory.research.eureka.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResearchEurekaClientUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchEurekaClientUserApplication.class, args);
    }
}
