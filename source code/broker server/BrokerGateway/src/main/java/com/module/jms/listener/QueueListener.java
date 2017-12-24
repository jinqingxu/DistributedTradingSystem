package com.module.jms.listener;

import com.module.bean.CancelOrder;
import com.module.bean.Firm;
import com.module.bean.Order;
import com.module.jms.sender.Sender;
import com.module.jms.sender.SenderImpl;
import com.module.service.FirmService;
import com.module.service.OrderService;
import com.module.service.ProductService;
import com.module.thread.ProcessCancelOrderThread;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.sql.Timestamp;

/**
 * Created by siqi.lou on 22/05/2017.
 */

@Component
public class QueueListener implements MessageListener {

    private FirmService firmService;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @Autowired
    @Qualifier("orderService")
    private OrderService orderService;



    private ApplicationContext context = null;

    public FirmService getFirmService() {
        return firmService;
    }

    public void setFirmService(FirmService firmService) {
        this.firmService = firmService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void onMessage(Message message)
    {
        if(context==null){
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            firmService = (FirmService) context.getBean("firmService");
            productService = (ProductService) context.getBean("productService");
            orderService = (OrderService) context.getBean("orderService");

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
                if(type.equals("Order")){
                    int id = m.getInt("id");
                    int orderType = 0;
                    String str = m.getString("orderType");
                    if(str.equals("limit")){
                        orderType=1;
                    }else if(str.equals("cancel")){
                        orderType=2;
                    }else if(str.equals("stop")){
                        orderType=3;
                    }
                    String code = m.getString("product");
                    String possession = m.getString("possession");
                    Double price = m.getDouble("price");
                    String firm = m.getString("firm");
                    int num = m.getInt("number");
                    Order order = new Order();
                    Firm f = firmService.queryByName(firm);
                    order.setVersion(0);
                    order.setFirm(f);
                    order.setGiven_price(price);
                    order.setTrorderid(id);
                    order.setType(orderType);
                    order.setStatus("unfinished");
                    order.setNumber(num);
                    order.setLeft_number(num);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    order.setTime(timestamp);

                    order.setType_possession(possession);
                    order.setProductCode(productService.queryByCode(code));
                    orderService.DealOrder(order);


                }else if(type.equals("CancelOrder")){
                    int id = m.getInt("order");
                    Order order = orderService.querybyid(id);
                    if(order.getLeft_number()>0||!order.getStatus().equals("canceled")){
                        CancelOrder cancelOrder = new CancelOrder();
                        cancelOrder.setCancelorder(order);
                        ProcessCancelOrderThread t = (ProcessCancelOrderThread)context.getBean("processCancelOrderThread");
                        t.executeCancelOrder(cancelOrder);
                    }
                    else {
                        Sender sender = new SenderImpl();
                        sender.sendCancelOrder(order);
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
