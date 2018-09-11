package com.example.service;

import com.example.dao.UserMapper;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User select() {
        return userMapper.select();
    }
}
