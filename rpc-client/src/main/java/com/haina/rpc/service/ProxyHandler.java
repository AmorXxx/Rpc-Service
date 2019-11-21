package com.haina.rpc.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProxyHandler implements InvocationHandler {
    private String ip;
    private int port;
    public ProxyHandler(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //开启socket连接
        Socket socket = new Socket(ip, port);
        //获得对象输出流，用来给服务端发送数据
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        //获得对象输入流，用来从服务端获取数据
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        try{
            //传递调用的接口
            output.writeObject(proxy.getClass().getInterfaces()[0]);
            //传递客户端调用的方法名
            output.writeUTF(method.getName());
            //传递客户端调用的方法参数类型列表
            output.writeObject(method.getParameterTypes());
            //传递客户端调用的方法参数列表
            output.writeObject(args);
            //手动刷新缓冲区，发送数据到服务端
            output.flush();
            //从服务端读取数据
            Object result = input.readObject();
            //判断结果数据，是异常则抛出
            if (result instanceof Throwable){
                throw (Throwable) result;
            }
            return result;
        } finally {
            socket.shutdownOutput();
        }
    }
}
