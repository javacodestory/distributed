package tech.codestory.research.eureka.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloConsumer() {
        String body = restTemplate.getForEntity("http://RESEARCH-EUREKA-CLIENT-USER/hello", String.class).getBody();
        return "remote : " + body;
    }
}

