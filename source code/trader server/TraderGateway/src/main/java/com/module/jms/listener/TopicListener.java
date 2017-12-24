package com.module.jms.listener;

import com.module.bean.PriceRecord;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.service.BrokerService;
import com.module.service.PriceRecordService;
import com.module.thread.ProcessStopOrderThread;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import websocketbot.BotEndpoint;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siqi.lou on 27/05/2017.
 */
@Component
public class TopicListener implements MessageListener {

    @Autowired
    @Qualifier("processStopOrderThread")
    ProcessStopOrderThread thread;

    @Autowired
    @Qualifier("brokerService")
    private BrokerService brokerService;

    @Autowired
    @Qualifier("priceRecordService")
    PriceRecordService priceRecordService;

    @Override
    public void onMessage(Message message)
    {
        if(thread==null){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            thread = (ProcessStopOrderThread) context.getBean("processStopOrderThread");
            brokerService = (BrokerService) context.getBean("brokerService");
            priceRecordService = (PriceRecordService) context.getBean("priceRecordService");
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
                if(type.equals("OrderBook")){
                    String code = m.getString("product");
                    String broker = m.getString("broker");
                    String askBook = m.getString("askBook");
                    String bidBook = m.getString("bidBook");
                    Double price = m.getDouble("price");
                    String time = m.getString("time");
                    OrderBook orderBook = new OrderBook();
                    int brokerid=(brokerService.querybyname(broker)).getId();
                    orderBook.setBrokerId(brokerid);
                    orderBook.setProductCode(code);
                    orderBook.setBrokerName(broker);
                    //JSONArray
                    JSONArray jask = JSONArray.fromObject(askBook);
                    List<DisRecord> ask = JSONArray.toList(jask,DisRecord.class);
                    JSONArray jbid = JSONArray.fromObject(bidBook);
                    List<DisRecord> bid = JSONArray.toList(jbid,DisRecord.class);
                    orderBook.setBuyBook(ask);
                    orderBook.setSellBook(bid);
                    RedisService redisService = new RedisServiceImpl();
                    PriceRecord p =new PriceRecord(price,code,broker,time);
                    priceRecordService.insert(p);
                    redisService.setOrderBook(orderBook);
                    //redisService.setPrice(code,broker,price);
                    redisService.setPriceRecord(p);
                    BotEndpoint botEndpoint=new BotEndpoint();
                    botEndpoint.sendAll(orderBook,price);
                    thread.execute(code,broker);
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
