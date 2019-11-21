package com.haina.rpc.service.impl;

import com.haina.rpc.service.IQueryService;

public class QueryServiceImpl implements IQueryService {
    private int count = 0;
    public String getUserById(int id) {
        return "user" + id;
    }

    public int count() {
        return count++;
    }
}
