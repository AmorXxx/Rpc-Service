package com.haina.rpc;

import com.haina.rpc.service.impl.RpcProvider;

public class RpcServerBootstrap  {
    public static void main(String[] args) {
        RpcProvider provider = new RpcProvider();
        provider.start();
    }
}
