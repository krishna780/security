package com.spring.security.Controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRest {

    public String userString() {
        return "user";
    }
}
