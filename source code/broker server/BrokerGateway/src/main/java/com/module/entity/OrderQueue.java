package com.module.entity;

import com.module.bean.Order;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class OrderQueue {
    private Queue<Order> orderQueue=new ArrayDeque<Order>(100);
    //0 represents no alive thread,1 represents alive thread
    private Integer status=0;

    public Queue<Order> getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(Queue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
    public void remove(Integer orderid){
        Queue<Order>q=new ArrayBlockingQueue<Order>(100);
        while(orderQueue.size()!=0){
            if(orderQueue.element().getId()!=orderid){
                q.add(orderQueue.element());
            }
            orderQueue.remove();
        }
        orderQueue=q;

    }
    public void change(Order or){
        Queue<Order>q=new ArrayBlockingQueue<Order>(100);
        while(orderQueue.size()!=0){
            if(orderQueue.element().getId()==or.getId()){
                q.add(or);
            }
            else{
                q.add(orderQueue.element());
            }
            orderQueue.remove();
        }
        orderQueue=q;
    }
}
