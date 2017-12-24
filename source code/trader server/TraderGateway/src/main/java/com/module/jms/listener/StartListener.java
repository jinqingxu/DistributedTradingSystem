package com.module.jms.listener;

import com.module.bean.Broker;
import com.module.service.BrokerService;
import com.module.service.serviceimpl.BrokerServiceImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    String firmName = "FirmA";

    private ApplicationContext context = null;

    @Autowired
    @Qualifier("brokerService")
    private BrokerService brokerService;

    public BrokerService getBrokerService() {
        return brokerService;
    }

    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (this.connections == null) {
            this.connections = new ArrayList<Connection>();
        }
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        brokerService = (BrokerService)context.getBean("brokerService");
        List<Broker> brokers = brokerService.getAllBrokers();
        for (int i = 0; i <brokers.size(); i++) {
            try {
                //url是brokerServer ["tcp://"+brokers.get(i).getIp+":61616"]
                String ip = brokers.get(i).getIp();
                String url = "tcp://"+ip+":61616";
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                // 本机ip为192.168.1.108,activemq发布在61616端口上的服务器
                Connection connection = connectionFactory.createConnection();
                connection.start();
                this.connections.add(connection);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                // trader既接收queue的信息，也接收topic的信息（orderbook的更新)
                Destination queueDes = session.createQueue(firmName);
                MessageConsumer consumer = session.createConsumer(queueDes);
                Destination topicDes = session.createTopic("Topic");
                MessageConsumer topicConsumer =session.createConsumer(topicDes);

                QueueListener listener = new QueueListener();
                consumer.setMessageListener(listener);
                TopicListener topicListener = new TopicListener();
                topicConsumer.setMessageListener(topicListener);

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
