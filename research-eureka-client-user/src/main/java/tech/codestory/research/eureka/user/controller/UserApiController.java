package tech.codestory.research.eureka.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author junyongliao
 * @date 2019/10/12
 * @since 1.0.0
 */
@RestController
public class UserApiController {
    @RequestMapping("/")
    public String home() {
        return "Hello world!";
    }
}
