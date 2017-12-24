package com.module.jms.sender;

import com.module.bean.*;
import com.module.dao.FirmDAO;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.service.FirmService;
import com.module.service.PriceRecordService;
import com.module.service.TransactionService;
import com.module.service.serviceimpl.FirmServiceImpl;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import websocketbot.BotEndpoint;

import javax.jms.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by siqi.lou on 23/05/2017.
 */
@Component("sender")
public class SenderImpl implements Sender{

    @Autowired
    @Qualifier("transactionService")
    private TransactionService transactionService;

    @Autowired
    @Qualifier("firmService")
    private FirmService firmService;

    @Autowired
    @Qualifier("priceRecordService")
    PriceRecordService priceRecordService;

    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;

    private int OrderBookDepth = 5;
    private String brokername = "BrokerA";

    public void sendTransaction(Transaction transaction){
        Double price = transaction.getPrice();
        int num = transaction.getNumber();
        int askOrder = transaction.getAskorder().getTrorderid();
        int bidOrder = transaction.getBidorder().getTrorderid();
        Timestamp time = transaction.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        String str = df.format(time);
        try {
            Firm ask = transactionService.getAskFirm(transaction);
            Firm bid = transactionService.getBidFirm(transaction);
            sendTransaction(ask.getName(), price,num,askOrder,bid.getName(),str);
            sendTransaction(bid.getName(), price,num,bidOrder,ask.getName(),str);
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    public void sendOrder(Order order) {
        try {
            sendOrderId(order.getFirm().getName(), order.getTrorderid(), order.getId());
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    public void sendOrderBook(OrderBook orderBook){
        String code = orderBook.getProductCode();
        PriorityQueue<DisRecord> askBook= orderBook.getDisBuyBook();
        PriorityQueue<DisRecord> bidBook= orderBook.getDisSellBook();
        PriceRecord p = priceRecordService.getNewPriceRecord(code);
        if(p==null)p.setPrice(0.0);
        p.setTime(new Timestamp(System.currentTimeMillis()));
        try {
            //for (Firm f : firmList) {
            List<DisRecord> askList = transOrderBook(askBook);
            List<DisRecord> bidList = transOrderBook(bidBook);
            JSONArray jask = JSONArray.fromObject(askList);
            JSONArray jbid = JSONArray.fromObject(bidList);
            sendOrderBook(code,jask.toString(),jbid.toString(),p.getPrice(),p.getTime());
            //sendOrderBook(code,transOrderBook(askBook),transOrderBook(bidBook),p.getPrice(),p.getTime());
            //}
        }catch (JMSException e){
            e.printStackTrace();
        }

    }

    public void sendOrderBook(OrderBook orderBook, Double price, Timestamp time){
        String code = orderBook.getProductCode();
        PriorityQueue<DisRecord> askBook= orderBook.getDisBuyBook();
        PriorityQueue<DisRecord> bidBook= orderBook.getDisSellBook();
        PriceRecord p = new PriceRecord(price,code,time);
        priceRecordService.insert(p);
        redisService.setPriceRecord(p);
        try {
            //for (Firm f : firmList) {
            List<DisRecord> askList = transOrderBook(askBook);
            List<DisRecord> bidList = transOrderBook(bidBook);
            JSONArray jask = JSONArray.fromObject(askList);
            JSONArray jbid = JSONArray.fromObject(bidList);
            sendOrderBook(code,jask.toString(),jbid.toString(),price,time);
            /*BotEndpoint botEndpoint=new BotEndpoint();
            List<DisRecord> buy=new ArrayList<DisRecord>();
            List<DisRecord> sell=new ArrayList<DisRecord>();
            buy.addAll(askBook);
            sell.addAll(bidBook);
            JSONArray jsonsell= JSONArray.fromObject(sell);
            JSONArray jsonbuy=JSONArray.fromObject(buy);
            botEndpoint.sendAll(jsonbuy.toString(),jsonsell.toString(),code,price,time);*/
            //}
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    public void sendCancelOrder(Order order){
        String status=order.getStatus();
        if(status.equals("unfinished")){
            if(order.getLeft_number()==0){
                status="fail";
            }
        }else {
            status = "success";
        }
        try {
            SendCancelOrder(order.getFirm().getName(), order.getTrorderid(), status);
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String status, String m) {

    }

    /*
         * 发送交易信息
         * 队列名称:FirmName
         * @param queueName
         * 消息内容：Object to json then to String
         */
    private void sendTransaction(String queueName, Double price, int num, int orderId,String firmName, String time) throws JMSException{
        String url="tcp://localhost:61616";
        Connection connection =null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            MapMessage m = session.createMapMessage();
            m.setString("type","transaction");
            m.setDouble("price",price);
            m.setInt("number",num);
            m.setInt("id",orderId);
            m.setString("firm",firmName);
            m.setString("time",time);
            producer.send(m);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 发送一条消息到指定的队列（目标）
     *
     * 队列名称:FirmName
     * @param queueName
     * 消息内容：Object to json then to String
     * @param FirmOrderId
     * @param orderId
     */
    public void sendOrderId(String queueName, int FirmOrderId,int orderId) throws JMSException{
        String url="tcp://localhost:61616";
        Connection connection =null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            MapMessage m = session.createMapMessage();
            m.setString("type","OrderId");
            m.setInt("orderIdinFirm",FirmOrderId);
            m.setInt("orderIdinBroker",orderId);
            producer.send(m);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendOrderBook(String pCode,String ask,String bid,double price,Timestamp time) throws JMSException{
        String url="tcp://localhost:61616";
        Connection connection =null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("Topic");
            MessageProducer producer = session.createProducer(destination);
            MapMessage m = session.createMapMessage();
            m.setString("type","OrderBook");
            m.setString("product",pCode);
            m.setString("askBook",ask);
            m.setString("bidBook",bid);
            m.setDouble("price",price);
            m.setString("time",time.toString());
            m.setString("broker",brokername);
            producer.send(m);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SendCancelOrder(String queueName, int orderId, String status) throws JMSException{
        String url="tcp://localhost:61616";
        Connection connection =null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            MapMessage m = session.createMapMessage();
            m.setString("type","CancelOrder");
            m.setInt("order",orderId);
            m.setString("status",status);
            producer.send(m);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  List<DisRecord> transOrderBook(PriorityQueue<DisRecord> disRecords){
        List<DisRecord> list = new ArrayList<DisRecord>();
        int i = 0;
        if(disRecords==null){
            return list;
        }
        while(i<5&&disRecords.size()>0){
            list.add(disRecords.element());
            disRecords.remove();
            i++;
        }
        return list;
    }
}
