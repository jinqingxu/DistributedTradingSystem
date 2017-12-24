package com.module.jms.listener;

import com.module.bean.Firm;
import com.module.entity.OrderBook;
import com.module.jms.sender.Sender;
import com.module.service.FirmService;
import com.module.service.OrderService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siqi.lou on 23/05/2017.
 */
@Component
public class StartListener implements ServletContextListener {
    private List<Connection> connections;

    @Autowired
    @Qualifier("firmService")
    FirmService firmService;
    @Autowired
    @Qualifier("sender")
    Sender sender;
    @Autowired
    @Qualifier("orderService")
    OrderService orderService;
    private String brokerName = "BrokerA";
    private ApplicationContext context = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        if (this.connections == null) {
            this.connections = new ArrayList<Connection>();
        }
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        firmService = (FirmService) context.getBean("firmService");
        sender=(Sender)context.getBean("sender");
        orderService=(OrderService)context.getBean("orderService");
        List<Firm> firms = firmService.getAllFirms();
        //Firm f = firmService.queryByName("trader");
        int size = 0;
        for (int i = 0; i <firms.size(); i++) {
            try {
                //connectionFactory = new ActiveMQConnectionFactory("不同Trader/Broker的activemq的发布地址,格式为tcp://ip地址:端口号(默认为61616)");
                // 实例
                Firm f = firms.get(i);
                String ip = f.getIp();
                String url = "tcp://"+ip+":61616";
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                // 本机ip为192.168.1.108,activemq发布在61616端口上的服务器

                Connection connection = connectionFactory.createConnection();
                connection.start();
                this.connections.add(connection);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

               /* 请注意,本行为Destination的设置.根据标准,Trader的Destination统一名为TraderDestination,
                * 而broker的Destination统一名为BrokerDestination,这里的Destination指消息发布的地址名
                */
                Destination destination = session.createQueue("BrokerA");
                MessageConsumer consumer = session.createConsumer(destination);
                QueueListener listener = new QueueListener();
                consumer.setMessageListener(listener);
//                   connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
            for(int i=0;i<this.connections.size();++i)
            {
                try {
                    this.connections.get(i).close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
    }
}
