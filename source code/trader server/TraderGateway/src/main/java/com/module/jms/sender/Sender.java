package com.module.jms.sender;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.ws.rs.core.Context;

/**
 * Created by siqi.lou on 01/06/2017.
 */

public interface Sender {

    void sendOrder(Order order);

    void sendCancelOrder(CancelOrder cancelOrder);

}
