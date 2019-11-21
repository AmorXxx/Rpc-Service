package com.haina.rpc.service.impl;

import com.haina.rpc.service.IHelloService;
import com.haina.rpc.service.IQueryService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RpcProvider {
    private int port;

    public RpcProvider(){
        this.port = 8080;
    }
    public RpcProvider(int port){
        this.port = port;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端开启成功！");
            QueryServiceImpl queryService = new QueryServiceImpl();
            while (true) {
                Socket client = null;
                ObjectInputStream input = null;
                ObjectOutputStream output = null;
                try {
                    //获取一个客户端请求，如果没有请求，程序会阻塞
                    client = serverSocket.accept();
                    //用来给客户端返回数据使用
                    output = new ObjectOutputStream(client.getOutputStream());
                    //用来从客户端获取请求使用
                    input = new ObjectInputStream(client.getInputStream());
                    //获取客户端调用的接口
                    //注意：读取数据的顺序必须与客户端写入顺序一致
                    Class serviceClass = (Class) input.readObject();
                    Object obj = findService(serviceClass);
                    if (obj==null){
                        throw new RuntimeException("没有"+serviceClass.getSimpleName()+"服务");
                    }
//                    Object obj = null;
//                    if (serviceClass == IHelloService.class) {
//                        obj = new HelloServiceImpl();
//                    } else if (serviceClass == IQueryService.class){
//                        obj = queryService;
//                    }
                    //获取客户端调用的方法名
                    String methodName = input.readUTF();
                    System.out.println("客户端调用" + methodName + "方法");
                    //获取参数类型列表
                    Class<?>[] paramTypeList= (Class<?>[]) input.readObject();
                    System.out.println("方法参数类型列表： " + Arrays.toString(paramTypeList));

                    //获取方法的参数值
                    Object[] arguments= (Object[]) input.readObject();
                    //使用类反射，获取方法对象
                    Method method = obj.getClass().getMethod(methodName, paramTypeList);
                    //调用该方法
                    Object result = method.invoke(obj, arguments);
                    System.out.println("服务端返回数据： " + result);
                    //返回数据给客户端
                    output.writeObject(result);
                } finally {
                    client.close();
                    output.close();
                    input.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<Object> serviceList;
    {
        serviceList = new ArrayList<Object>();
        serviceList.add(new HelloServiceImpl());
        serviceList.add(new QueryServiceImpl());
        serviceList.add(new FlightServiceImpl());
    }
    private Object findService(Class clazz){
        for (Object obj : serviceList){
            if (clazz.isAssignableFrom(obj.getClass())){
                return obj;
            }
        }
        return null;
    }
}
