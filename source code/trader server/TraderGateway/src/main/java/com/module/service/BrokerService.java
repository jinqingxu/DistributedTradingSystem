package com.module.service;

import com.module.bean.Broker;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public interface BrokerService {
    public List<Broker> getAllBrokers();
    public Broker querybyid(Integer id);
    public void insert(Broker tr);
    public void update(Broker tr);
    public void delete(Broker tr);
    public Broker querybyname(String name);
}
