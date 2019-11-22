package com.haina.rpc;

import com.haina.rpc.service.FlightService;
import com.haina.rpc.service.ProxyHandler;
import com.haina.rpc.service.RpcConsumer;

public class RpcFlightServiceTest {
    public static void main(String[] args) {
        new ProxyHandler("127.0.0.1", 8080);
        FlightService flightService = RpcConsumer.getService(FlightService.class, "127.0.0.1", 8080);
        System.out.println(flightService.getFlightNo());
    }
}
