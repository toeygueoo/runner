package com.azthink.runner.core.service.impl;

import com.azthink.runner.core.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService{
    @Override
    public String say(String name) {
        return "Hello, " + name;
    }
}
