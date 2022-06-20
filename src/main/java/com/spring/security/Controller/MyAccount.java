package com.spring.security.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAccount {

    @GetMapping("/myAccount")
    public String nyAccount(){
        return "myAccount";
    }

}
