package com.module.service;

import com.module.bean.Order;
import com.module.dao.OrderDAO;
import com.module.entity.OrderBook;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public interface OrderService {
    public int DealOrder(Order or)throws Exception;
    public OrderDAO getOrderDAO();
    public Order querybyid(Integer id);
    public List<Order> getAllOrders();
    public void insert(Order or);
    public void update(Order or);
    List<Order> getUnfinishedOrders();
    public OrderBook dbToOrderBook();



}
