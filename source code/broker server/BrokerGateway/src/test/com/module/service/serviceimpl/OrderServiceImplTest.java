package com.module.service.serviceimpl;


import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.service.CancelOrderService;
import com.module.service.FirmService;
import com.module.service.OrderService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.module.bean.Firm;
import com.module.bean.Transaction;
import com.module.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

/**
 * Created by jinqingxu on 2017/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations={"classpath:/applicationContext.xml",
})
public class OrderServiceImplTest {
    @Resource
    private OrderService orderService;
@Resource
private CancelOrderService cancelOrderService;
    @Resource
    private FirmService firmService;
    @Test
    public void dbToOrderBook() throws Exception {
        OrderBook orderBook=orderService.dbToOrderBook();
        RedisService redisService=new RedisServiceImpl();
        redisService.setOrderBook(orderBook);
        PriorityQueue<DisRecord>l1=orderBook.getDisBuyBook();
        PriorityQueue<DisRecord>l2=orderBook.getDisSellBook();
    }
    @Test
    public void display() throws  Exception{
        /*Order or1=new Order();
        or1.setLeft_number(10);
        or1.setType(0);
        or1.setType_possession("bid");
        Firm f2=firmService.queryById(2);
        or1.setFirm(f2);
        or1.setNumber(10);
        or1.setGiven_price(10.0);
        or1.setStatus("unfinished");
        or1.setVersion(0);
        or1.setTrorderid(1);
        Order or2=new Order();
        or2.setLeft_number(10);
        or2.setType(1);
        or2.setType_possession("bid");
        f2=firmService.queryById(2);
        or2.setFirm(f2);
        or2.setNumber(10);
        or2.setGiven_price(45240.0);
        or2.setStatus("unfinished");
        or2.setVersion(0);
        or2.setTrorderid(1);
        orderService.display(or1);
        orderService.display(or2);*/
        CancelOrder cancelOrder=new CancelOrder();
        Order c=this.orderService.querybyid(5);
        cancelOrder.setCancelorder(c);
        cancelOrder.setTraderorderid(1);
        //cancelOrderService.display(cancelOrder);

    }



}
