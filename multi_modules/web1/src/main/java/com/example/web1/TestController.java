package com.example.web1;

import com.example.dao1.UserMapper;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test2")
    public User select() {
        return userMapper.select();
    }
}
