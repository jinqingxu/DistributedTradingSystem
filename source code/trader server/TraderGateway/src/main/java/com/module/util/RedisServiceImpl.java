package com.module.util;

import com.module.bean.Order;
import com.module.bean.PriceRecord;
import com.module.entity.DisRecord;
import com.module.util.Serialization.ListTranscoder;
import com.module.util.Serialization.ObjectTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import com.module.entity.OrderBook;
import com.module.entity.OrderQueue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by siqi.lou on 21/05/2017.
 */
public class RedisServiceImpl implements RedisService {


    @Override
    public void setStopOrderQueue(String code, String firm, OrderQueue p) {
        Jedis jedis = new Jedis();
        List<Order> orders = Queue2List(p.getOrderQueue());
        String status = p.getStatus().toString();
        ListTranscoder<Order> listTranscoder= new ListTranscoder<Order>();
        jedis.set(("Stop"+code+firm).getBytes(),listTranscoder.serialize(orders));
        jedis.set("StopStatus"+code+firm,status);
    }

    @Override
    public OrderQueue getStopOrderQueue(String code, String firm) {
        Jedis jedis = new Jedis();
        List<Order> orders = new ArrayList<Order>();
        OrderQueue q = new OrderQueue();
        ListTranscoder<Order> listTranscoder= new ListTranscoder<Order>();
        if(jedis.exists(("Stop"+code+firm).getBytes())){
            String status = "-1";
            try{
                orders = listTranscoder.deserialize(jedis.get(("Stop"+code+firm).getBytes()));
                status = jedis.get("StopStatus"+code+firm);
            }catch (Exception e){
                e.printStackTrace();
            }
            PriorityQueue<Order> p = List2PQ(orders,q.getComp());
            q.setOrderQueue(p);
            q.setStatus(Integer.parseInt(status));
            return q;
        }
        return null;
    }


    private List<Order> Queue2List(Queue<Order> orders){
        List<Order> list =new ArrayList<Order>();
        for (Order order : orders) {
            list.add(order);
        }
        return list;
    }

    private Queue<Order> List2Queue(List<Order> list){
        Queue<Order> queue = new ArrayDeque<Order>();
        for(Order order:list){
            queue.add(order);
        }
        return queue;
    }


    private PriorityQueue<Order> List2PQ(List<Order> list,Comparator<Order> c){
        PriorityQueue<Order> p = new PriorityQueue<Order>(list.size(),c);
        for(Order record:list){
            p.add(record);
        }
        return p;
    }


    @Override
    public OrderBook getOrderBook(String code,String broker) {
        Jedis jedis = new Jedis();
        ListTranscoder<DisRecord> listTranscoder= new ListTranscoder<DisRecord>();
        if(jedis.exists(("OB-ask" + code + broker).getBytes())
                && jedis.exists(("OB-bid" + code + broker).getBytes())) {
            try {
                List<DisRecord> askBook = listTranscoder.deserialize(jedis.get(("OB-ask" + code + broker).getBytes()));
                List<DisRecord> bidBook = listTranscoder.deserialize(jedis.get(("OB-bid" + code + broker).getBytes()));
                OrderBook orderBook = new OrderBook();
                orderBook.setProductCode(code);
                orderBook.setBrokerName(broker);
                orderBook.setBuyBook(askBook);
                orderBook.setSellBook(bidBook);
                return orderBook;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void setOrderBook(OrderBook orderBook) {
        Jedis jedis = new Jedis();
        List<DisRecord> askBook = orderBook.getBuyBook();
        List<DisRecord> bidBook = orderBook.getSellBook();
        String code = orderBook.getProductCode();
        String broker = orderBook.getBrokerName();
        ListTranscoder<DisRecord> listTranscoder= new ListTranscoder<DisRecord>();
        jedis.set(("OB-ask"+code+broker).getBytes(),listTranscoder.serialize(askBook));
        jedis.set(("OB-bid"+code+broker).getBytes(),listTranscoder.serialize(bidBook));
    }


    @Override
    public void setPrice(String code, String broker, Double price) {
        Jedis jedis = new Jedis();
        jedis.set(("Price"+code+broker),price.toString());

    }

    @Override
    public Double getPrice(String code, String broker) {
        Jedis jedis = new Jedis();
        Double price = 0.0;
        if(jedis.exists("Price"+code+broker)){
            price = Double.parseDouble(jedis.get("Price"+code+broker));
        }
        return price;
    }

    @Override
    public PriceRecord getPriceRecord(String code, String broker) {
        Jedis jedis = new Jedis();
        PriceRecord p = new PriceRecord();
        p.setCode(code);
        p.setBrokerName(broker);
        if(jedis.exists("Price"+code+broker)){
            p.setPrice( Double.parseDouble(jedis.get("Price"+code+broker)));
            p.setTime(Timestamp.valueOf(jedis.get("Time"+code+broker)));
            return p;
        }
        return null;
    }

    @Override
    public void setPriceRecord(PriceRecord p) {
        Jedis jedis = new Jedis();
        jedis.set(("Price"+p.getCode()+p.getBrokerName()),p.getPrice().toString());
        jedis.set(("Time"+p.getCode()+p.getBrokerName()),p.getTime().toString());
    }
}
