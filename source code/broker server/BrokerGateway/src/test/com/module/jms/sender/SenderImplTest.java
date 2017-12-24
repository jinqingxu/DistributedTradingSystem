package com.module.jms.sender;

import com.module.bean.Firm;
import com.module.bean.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

/**
 * Created by siqi.lou on 23/05/2017.
 */
@Component
public class SenderImplTest {

    @Test
    public void sendTransaction() throws Exception {
    }

    @Test
    public void sendOrder() throws Exception {
    }

    @Test
    public void sendOrderId() throws Exception {
        Sender sender = new SenderImpl();
        Order order = new Order();
        Firm f = new Firm();
        f.setName("trader");
        order.setFirm(f);
        order.setId(1);
        order.setTrorderid(5);
        sender.sendOrder(order);
    }

}