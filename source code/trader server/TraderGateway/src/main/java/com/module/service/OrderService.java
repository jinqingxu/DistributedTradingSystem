package com.module.service;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.dao.OrderDAO;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public interface OrderService {
    public List<Order> getAllOrder();
    public void update(Order or);
    public void updateStatus(Integer id,String status);
    public void updateLeftNumber(Integer id,Integer leftnumber);
    public Order queryById(Integer id);
    public void insert(Order or);
    public void delete(Order or);
    //把broker端传回的ID存入数据库，id是本地orderId
    public void orderReceived(int id, int brokerId);
    public void placeOrder(Order or);
    //查询所有的cancelorder
    public List<Order>getAllUnfinishedOrder();
    //查询某个broker的cancelorer
    public List<Order> getAllUnfinishedOrder(Integer id);

    List<Order> getAllOrder(int id);
    public void vamp()throws Exception;


}
