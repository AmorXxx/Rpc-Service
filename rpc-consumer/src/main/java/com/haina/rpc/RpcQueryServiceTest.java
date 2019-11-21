package com.haina.rpc;

import com.haina.rpc.service.IHelloService;
import com.haina.rpc.service.IQueryService;
import com.haina.rpc.service.ProxyHandler;
import com.haina.rpc.service.RpcConsumer;

import java.lang.reflect.Proxy;

public class RpcQueryServiceTest {
    public static void main(String[] args) {
        ProxyHandler handler = new ProxyHandler("127.0.0.1", 8080);
        IQueryService  iQueryService= RpcConsumer.getService(IQueryService.class,"127.0.0.1",8080);
        System.out.println(iQueryService.getUserById(100));
        System.out.println(iQueryService.count());
    }
}