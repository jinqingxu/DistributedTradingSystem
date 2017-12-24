package com.module.dao;


import com.module.bean.Order;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public interface OrderDAO {
    public List<Order>  getAllOrder();
    public void update(Order or);
    public int updateStatus(Integer id,String status);
    public int updateLeftNumber(Integer id,Integer leftnumber);
    public Order queryById(Integer id);
    public void insert(Order or);
    public void delete(Order or);

}
