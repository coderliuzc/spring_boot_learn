package com.example.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("/test1")
    public String test1() {

        return "test1() doing something";
    }

    @Login
    @GetMapping("/test2")
    public String test2(HttpServletRequest request) {

        return "test2() doing something";
    }
}
