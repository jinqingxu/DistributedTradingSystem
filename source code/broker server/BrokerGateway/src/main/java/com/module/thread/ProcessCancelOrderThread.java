package com.module.thread;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.entity.CancelOrderQueue;
import com.module.entity.OrderQueue;
import com.module.jms.sender.Sender;
import com.module.service.CancelOrderService;
import com.module.service.OrderService;
import com.module.util.RedisService;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jinqingxu on 2017/6/3.
 */
public class ProcessCancelOrderThread {
    private CancelOrderQueue cancelOrderQueue;
    private RedisService redisService;
    private String productCode;
    private CancelOrderService cancelOrderService;
    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setCancelOrderQueue(CancelOrderQueue cancelOrderQueue) {
        this.cancelOrderQueue = cancelOrderQueue;
    }

    public CancelOrderQueue getCancelOrderQueue() {
        return cancelOrderQueue;
    }

    public CancelOrderService getCancelOrderService() {
        return cancelOrderService;
    }

    public void setCancelOrderService(CancelOrderService cancelOrderService) {
        this.cancelOrderService = cancelOrderService;
    }
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }



    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public RedisService getRedisService() {
        return redisService;
    }



    public void executeCancelOrder(CancelOrder cancelOrder){
        productCode=cancelOrder.getCancelorder().getProductCode().getCode();
        cancelOrderService.insert(cancelOrder);
        cancelOrderQueue=redisService.getCancelOrderQueue(productCode);
        cancelOrderQueue.getCancelOrderQueue().add(cancelOrder);
        redisService.setCancelOrderQueue(productCode,cancelOrderQueue);
        cancelOrderQueue=redisService.getCancelOrderQueue(productCode);
        //if no alive thread and orderqueue.size()>0,new a thread
        if(cancelOrderQueue.getStatus()==0) {
            if (cancelOrderQueue.getCancelOrderQueue().size() != 0) {
                new ProcessCancelOrderThread.Worker().start();
            }
        }

    }
    private class Worker extends Thread {
        @Override
        public void run() {
            cancelOrderQueue.setStatus(1);
            redisService.setCancelOrderQueue(productCode,cancelOrderQueue);
            String status="";
            int re=0;
            while(cancelOrderQueue.getCancelOrderQueue().size()!=0){
                CancelOrder top=cancelOrderQueue.getCancelOrderQueue().element();
                try {
                    re = cancelOrderService.DealOrder(top, productCode);

                }
                catch (Exception e){

                    e.printStackTrace();
                }

                cancelOrderQueue.getCancelOrderQueue().remove();
            }
            cancelOrderQueue.setStatus(0);
            redisService.setCancelOrderQueue(productCode,cancelOrderQueue);

        }
    }
}
