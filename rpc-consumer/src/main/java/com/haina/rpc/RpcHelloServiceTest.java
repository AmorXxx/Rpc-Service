package com.haina.rpc;

import com.haina.rpc.service.IHelloService;
import com.haina.rpc.service.ProxyHandler;
import com.haina.rpc.service.RpcConsumer;

import java.lang.reflect.Proxy;

public class RpcHelloServiceTest {

    public static void main(String[] args) {
        //注册中心，基于zookeeper，向同一个服务名下，创建以自己ip为名称的临时节点
        //通过服务名，取出该服务节点下的所有子节点，子节点就是该服务的机器列表
        //List<String> ipPortList = getServerIpListByServerName(serverName);
        //String port = balance(ipPortList);
        ProxyHandler handler = new ProxyHandler("127.0.0.1", 8080);
        IHelloService helloService = RpcConsumer.getService(IHelloService.class,"127.0.0.1", 8080);
        System.out.println(helloService.sayHello("Amor"));
    }
}
