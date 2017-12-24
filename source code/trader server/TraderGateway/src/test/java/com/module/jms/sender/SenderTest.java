package com.module.jms.sender;

import com.module.bean.Broker;
import com.module.bean.Order;
import com.module.bean.Product;
import com.module.service.OrderService;
import org.junit.Test;

import javax.ws.rs.core.Context;

import static org.junit.Assert.*;

/**
 * Created by siqi.lou on 01/06/2017.
 */
import junit.framework.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/*import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})*/
public class SenderTest {

    //@Autowired
    //@Qualifier("orderService")
    //OrderService orderService;


    @Test
    public void send() throws Exception {
        Sender sender = new SenderImpl();
        Order order = new Order();
        Broker broker =new Broker();
        Product product = new Product();
        product.setCode("Fe1709");
        broker.setName("xxx");
        order.setBroker(broker);
        order.setType(1);
        order.setGiven_price(93.24);
        order.setId(1);
        order.setPossession("ask");
        order.setProduct(product);
        sender.sendOrder(order);
    }
}