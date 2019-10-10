package tech.codestory.research.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaServer
public class ResearchEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchEurekaServerApplication.class, args);
    }

}
