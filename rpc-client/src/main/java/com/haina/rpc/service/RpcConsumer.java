package com.haina.rpc.service;

import java.lang.reflect.Proxy;

public class RpcConsumer {
    private static ProxyHandler proxyHandler;
    public static <T>T getService(Class<T> clazz,String ip,int port){
        if (proxyHandler == null){
            proxyHandler = new ProxyHandler(ip, port);
        }
        return (T) Proxy.newProxyInstance(RpcConsumer.class.getClassLoader(),new Class<?>[]{clazz},proxyHandler);
    }
}
