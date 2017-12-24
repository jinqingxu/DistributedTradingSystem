package com.module.service;

import com.module.bean.Trader;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public interface TraderService {
    public List<Trader> getAllTraders();
    public Trader querybyid(Integer id);
    public void insert(Trader tr);
    public void update(Trader tr);
    public void delete(Trader tr);
}
