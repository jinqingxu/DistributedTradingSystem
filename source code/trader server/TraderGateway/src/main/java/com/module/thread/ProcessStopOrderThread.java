package com.module.thread;

import com.module.bean.Broker;
import com.module.bean.Order;
import com.module.entity.OrderBook;
import com.module.entity.OrderQueue;
import com.module.jms.sender.Sender;
import com.module.service.BrokerService;
import com.module.service.OrderService;
import com.module.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class ProcessStopOrderThread {
    private OrderService orderService;
    private RedisService redisService;
    private String productCode;
    private BrokerService brokerService;

    @Autowired
    @Qualifier("sender")
    Sender sender;

    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public BrokerService getBrokerService() {
        return brokerService;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
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
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderService getOrderService() {
        return orderService;
    }


    //下stoporder的时候调用
    //orderbook变动了也要调用
    public void execute(String productCode,String brokername) {
        OrderQueue orderQueue=redisService.getStopOrderQueue(productCode,brokername);
        //if no alive thread and orderqueue.size()>0,new a thread
        if(orderQueue.getStatus()==0) {
            if (orderQueue.getOrderQueue().size() != 0) {
                new Worker().start();
            }
        }
    }
    private class Worker extends Thread {
        private String productCode;
        public String getProductCode() {
            return productCode;
        }
        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }
        @Override
        public void run() {
            List<Broker> brokers = brokerService.getAllBrokers();
            for (int i = 0; i < brokers.size(); ++i) {
                OrderQueue orderQueue = redisService.getStopOrderQueue(productCode,brokers.get(i).getName());
                PriorityQueue<Order> q = orderQueue.getOrderQueue();
                PriorityQueue<Order> copy = new PriorityQueue<Order>(1, compForOrder);
                while (q.size() != 0) {
                    Order or = orderQueue.getOrderQueue().element();
                    orderQueue.getOrderQueue().remove();
                    OrderBook orderBook = redisService.getOrderBook(productCode,brokers.get(i).getName());
                    //ask
                    if (or.getPossession().equals("ask")) {
                        if (orderBook.getBuyBook().get(0).getPrice() <= or.getGiven_price()) {
                            //change to market order
                            or.setType(0);
                            orderService.update(or);
                            sender.sendOrder(or);
                        } else {
                            copy.add(or);
                        }
                    } else {
                        if (orderBook.getSellBook().get(0).getPrice() >= or.getGiven_price()) {
                            //change to market order
                            or.setType(0);
                            orderService.update(or);
                            sender.sendOrder(or);
                        } else {
                            copy.add(or);
                        }

                    }
                    orderQueue = redisService.getStopOrderQueue(productCode,brokers.get(i).getName());
                    q = orderQueue.getOrderQueue();
                }
                orderQueue.setOrderQueue(copy);
                redisService.setStopOrderQueue(productCode,brokers.get(i).getName(),orderQueue);
            }
         }
   }
}
