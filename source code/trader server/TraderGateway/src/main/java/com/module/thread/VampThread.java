package com.module.thread;

import com.module.service.OrderService;

import java.util.Calendar;

/**
 * Created by jinqingxu on 2017/6/8.
 */
public class VampThread {
    private OrderService orderService;

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void execute() {
        new VampThread.Worker().start();
    }
    private class Worker extends Thread {
        @Override
        public void run() {
            try {
                Calendar c;
                while(true){
                    c=Calendar.getInstance();//时间对象
                    int mm=120;//默认线程间隔时间
                    System.out.println("mm:"+mm+"  time:"+c.get(Calendar.SECOND));
                    System.out.println("mm:"+mm);
                    Thread.sleep(mm*1000L);
                    if(c.get(Calendar.SECOND)==0)//条件执行
                        System.out.println("run Thread");
                        orderService.vamp();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
