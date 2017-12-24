package com.module.jms.listener;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.bean.Transaction;
import com.module.service.BrokerService;
import com.module.service.OrderService;
import com.module.service.TransactionService;
import com.module.thread.ProcessStopOrderThread;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by siqi.lou on 22/05/2017.
 */

@Component
public class QueueListener implements MessageListener {

    private OrderService orderService;

    private TransactionService transactionService;

    private ApplicationContext context;

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void onMessage(Message message)
    {
        if(orderService==null){
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            orderService = (OrderService) context.getBean("orderService");;
            transactionService = (TransactionService) context.getBean("transactionService");
        }
        try
        {
            if (message instanceof TextMessage)
            {
                TextMessage tm = (TextMessage) message;

                //System.out.println("监听消息：" + tm.getText());
            }else if(message instanceof MapMessage){
                MapMessage m = (MapMessage)message;
                String type =m.getString("type");
                //receive Order Ack from broker
                if(type.equals("OrderId")){
                    int firmId = m.getInt("orderIdinFirm");
                    int brokerId = m.getInt("orderIdinBroker");
                    orderService.orderReceived(firmId,brokerId);
                }//Transaction & update Order
                else if(type.equals("transaction")){
                    int orderId = m.getInt("id");
                    Order order = orderService.queryById(orderId);
                    int num = m.getInt("number");
                    int left = order.getLeft_number()-num;
                    order.setLeft_number(left);
                    orderService.update(order);
                    Double price = m.getDouble("price");
                    String firm = m.getString("firm");
                    Timestamp time  = Timestamp.valueOf(m.getString("time"));
                    Transaction transaction = new Transaction(price,num,order,firm,time);
                    transactionService.insert(transaction);
                }//Cancel Order Reply
                else if(type.equals("CancelOrder")){
                    int id  = m.getInt("order");
                    String status = m.getString("status");
                    Order order = orderService.queryById(id);
                    if(status.equals("success")){
                        order.setStatus("canceled");
                        orderService.update(order);
                    }
                }
            }
            else
            {
                System.out.println("消息类型：" + message.getClass());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
