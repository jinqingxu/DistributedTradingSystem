package com.module.entity;

import com.module.bean.Order;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class OrderQueue {
    private PriorityQueue<Order> orderQueue;
    //0 represents no alive thread,1 represents alive thread
    private Integer status;

    public PriorityQueue<Order> getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(PriorityQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    Comparator<Order> compForOrder = new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
            if(o1.getTime().getTime()<o2.getTime().getTime()){
                return -1;
            }
            else if(o1.getTime().getTime()>o2.getTime().getTime()){
                return 1;

            }
            else{
                return 0;
            }
        }


    };

    public Comparator<Order> getComp(){
        return compForOrder;
    }
}
