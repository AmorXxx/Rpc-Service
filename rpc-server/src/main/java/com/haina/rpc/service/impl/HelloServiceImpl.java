package com.haina.rpc.service.impl;

import com.haina.rpc.service.IHelloService;

public class HelloServiceImpl implements IHelloService {
    public String sayHello(String name) {
        return "hello " + name;
    }
}
