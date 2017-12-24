package com.module.entity;

import com.module.bean.CancelOrder;
import com.module.bean.Order;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jinqingxu on 2017/6/2.
 */
public class CancelOrderQueue {
    private Queue<CancelOrder> cancelOrderQueue=new ArrayBlockingQueue<CancelOrder>(100);
    //0 represents no alive thread,1 represents alive thread
    private Integer status=0;

    public Queue<CancelOrder> getCancelOrderQueue() {
        return cancelOrderQueue;
    }

    public void setCancelOrderQueue(Queue<CancelOrder> cancelOrderQueue) {
        this.cancelOrderQueue = cancelOrderQueue;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
