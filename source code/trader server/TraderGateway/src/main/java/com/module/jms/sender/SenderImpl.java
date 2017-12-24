package com.module.jms.sender;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by siqi.lou on 01/06/2017.
 */
@Component("sender")
public class SenderImpl implements Sender{

    private String firm = "FirmA";

    @Override
    public void sendOrder(Order order) {
        String type;
        switch (order.getType()){
            case 0:
                type="market";
                break;
            case 1:
                type="limit";
                break;
            case 2:
                type="cancel";
                break;
            default:
                type="stop";
        }
        try{
            sendOrder(order,order.getBroker().getName(),type);
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    public void sendCancelOrder(CancelOrder cancelOrder){
        Order order = cancelOrder.getCancelorder();
        int id = order.getBrokerorderid();
        String broker = order.getBroker().getName();
        try{
            sendCancelOrder(broker,id);
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    private void sendOrder(Order order,String broker,String type) throws JMSException {
        String url = "tcp://localhost:61616";
        Connection connection = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(broker);
            MessageProducer producer = session.createProducer(destination);
            MapMessage m = session.createMapMessage();
            m.setString("type", "Order");
            m.setString("product", order.getProduct().getCode());
            m.setString("orderType", type);
            m.setInt("number",order.getNumber());
            m.setObject("id", order.getId());
            m.setDouble("price", order.getGiven_price());
            m.setString("possession", order.getPossession());
            m.setString("firm",firm);
            producer.send(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCancelOrder(String broker,int id) throws JMSException{
        String url = "tcp://localhost:61616";
        Connection connection = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(broker);
            MessageProducer producer = session.createProducer(destination);
            MapMessage m = session.createMapMessage();
            m.setString("type", "CancelOrder");
            m.setInt("order", id);
            producer.send(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
