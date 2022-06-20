package com.spring.security.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyBalance {

    @GetMapping("/myBalance")
    public String nyAccount(){
        return "balance";
    }

}
