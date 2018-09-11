package com.example.web;

import com.example.entity.User;
import com.example.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private ITestService testService;

    @GetMapping("/test")
    public User test1() {
        return testService.select();
    }


}
