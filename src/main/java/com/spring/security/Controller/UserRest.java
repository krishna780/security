package com.spring.security.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRest {

    @GetMapping("/userRest")
    public String userString() {
        return "user";
    }
}
