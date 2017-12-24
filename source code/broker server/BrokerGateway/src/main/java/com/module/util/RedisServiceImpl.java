package com.module.util;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.bean.PriceRecord;
import com.module.entity.CancelOrderQueue;
import com.module.entity.OrderBook;
import com.module.entity.OrderQueue;
import com.module.entity.Record;
import com.module.util.Serialization.ListTranscoder;
import com.module.util.Serialization.QueueTran;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by siqi.lou on 21/05/2017.
 */
public class RedisServiceImpl implements RedisService {
    private int limitNumber=10;

    @Override
    public void setCancelOrderQueue(String productCode, CancelOrderQueue cancelOrderQueue) {
        Jedis jedis = new Jedis();
        Queue<CancelOrder> cancelOrders =cancelOrderQueue.getCancelOrderQueue();
        ListTranscoder<CancelOrder> listTranscoder= new ListTranscoder<CancelOrder>();
        QueueTran<CancelOrder> tran = new QueueTran<CancelOrder>();
        String status = cancelOrderQueue.getStatus().toString();
        List<CancelOrder> list = tran.toList(cancelOrders);
        jedis.set(("CancelOrder"+productCode).getBytes(),listTranscoder.serialize(list));
        jedis.set("CancelOrderStatus"+productCode,status);
    }

    @Override
    public CancelOrderQueue getCancelOrderQueue(String productCode) {
        Jedis jedis=new Jedis();
        ListTranscoder<CancelOrder> listTranscoder= new ListTranscoder<CancelOrder>();
        if(!jedis.exists(("CancelOrder"+productCode).getBytes())){
            return null;
        }
        try {
            List<CancelOrder> list = listTranscoder.deserialize(jedis.get(("CancelOrder"+productCode).getBytes()));
            Queue<CancelOrder> queue = new ArrayDeque<CancelOrder>();
            queue.addAll(list);
            int status = Integer.parseInt(jedis.get("CancelOrderStatus"+productCode));
            CancelOrderQueue cancelOrderQueue = new CancelOrderQueue();
            cancelOrderQueue.setCancelOrderQueue(queue);
            cancelOrderQueue.setStatus(status);
            return  cancelOrderQueue;
         }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderBook getOrderBook(String productCode) {
        Jedis jedis=new Jedis();
        ListTranscoder<Record> listTranscoder= new ListTranscoder<Record>();
        PriorityQueue<Record> ask = new PriorityQueue<Record>(1,compForBuy);
        PriorityQueue<Record> bid = new PriorityQueue<Record>(1,compForSell);
        if(!jedis.exists(("ask" + productCode).getBytes())&&!jedis.exists(("bid" + productCode).getBytes())){
            return null;
        }
        try {
            ask=List2PQ(listTranscoder.deserialize(jedis.get(("ask" + productCode).getBytes())),compForBuy);
            bid=List2PQ(listTranscoder.deserialize(jedis.get(("bid" + productCode).getBytes())),compForSell);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            OrderBook orderBook =new OrderBook();
            orderBook.setProductCode(productCode);
            orderBook.setBuyBook(ask);
            orderBook.setSellBook(bid);
            return orderBook;
        }
    }

    @Override
    public void setOrderBook(OrderBook orderbook){
        Jedis jedis = new Jedis();
        PriorityQueue<Record> ask =orderbook.getBuyBook();
        PriorityQueue<Record> bid =orderbook.getSellBook();
        ListTranscoder<Record> listTranscoder= new ListTranscoder<Record>();
        jedis.set(("ask"+orderbook.getProductCode()).getBytes(),listTranscoder.serialize(PQ2List(ask)));
        jedis.set(("bid"+orderbook.getProductCode()).getBytes(),listTranscoder.serialize(PQ2List(bid)));
    }

    @Override

    public OrderQueue getRequestQueue(String code) {
        Jedis jedis=new Jedis();
        ListTranscoder<Order> listTranscoder= new ListTranscoder<Order>();
        OrderQueue orderQueue = new OrderQueue();
        Queue<Order> q=new ArrayDeque<Order>();
        // 0 - 无进程处理
        // 1 - 有进程正在处理
        int status=-1;
        if( (!jedis.exists(("RequestQueue"+code).getBytes()) )
                || ( !jedis.exists(("RequestQueueStatus"+code))) ){
            return null;
        }
        try{
            List<Order> list=listTranscoder.deserialize(jedis.get(("RequestQueue"+code).getBytes()));
            status = Integer.parseInt(jedis.get("RequestQueueStatus"+code));
            q=List2Queue(list);

        }catch (IOException e){
            e.printStackTrace();
        }
        orderQueue.setOrderQueue(q);
        orderQueue.setStatus(status);
        return orderQueue;
    }

    @Override

    public void setRequestQueue(String code, OrderQueue orderQueue) {

        Jedis jedis = new Jedis();
        Queue<Order> q =orderQueue.getOrderQueue();
        ListTranscoder<Order> listTranscoder= new ListTranscoder<Order>();
        jedis.set(("RequestQueue"+code).getBytes(),listTranscoder.serialize(Queue2List(q)));
        jedis.set("RequestQueueStatus"+code,orderQueue.getStatus().toString());
    }

    @Override

    public OrderQueue getLimitOrderQueue(String code) {
        Jedis jedis=new Jedis();
        ListTranscoder<Order> listTranscoder= new ListTranscoder<Order>();
        OrderQueue orderQueue = new OrderQueue();
        Queue<Order> q=new ArrayDeque<Order>();
        int status=-1;
        if( (!jedis.exists(("LimitQueue"+code).getBytes()) )
                || ( !jedis.exists(("LimitQueueStatus"+code))) ){
            return null;
        }
        try{
            List<Order> list=listTranscoder.deserialize(jedis.get(("LimitQueue"+code).getBytes()));
            status = Integer.parseInt(jedis.get("LimitQueueStatus"+code));
            q=List2Queue(list);
        }catch (IOException e){
            e.printStackTrace();
        }
        orderQueue.setOrderQueue(q);
        orderQueue.setStatus(status);
        return orderQueue;
    }

    @Override

    public void setLimitOrderQueue(String code,OrderQueue orderQueue) {

        Jedis jedis = new Jedis();
        Queue<Order> q = orderQueue.getOrderQueue();
        ListTranscoder<Order> listTranscoder= new ListTranscoder<Order>();
        jedis.set(("LimitQueue"+code).getBytes(),listTranscoder.serialize(Queue2List(q)));
        jedis.set("LimitQueueStatus"+code,orderQueue.getStatus().toString());
    }

    Comparator<Record> compForBuy = new Comparator<Record>() {
        public int compare(Record o1, Record o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return 1;
            } else if (o1.getPrice().equals(o2.getPrice())) {
                if(o1.getTime().getTime()<o2.getTime().getTime()){
                    return -1;
                }
                else if(o1.getTime().getTime()>o2.getTime().getTime()){
                    return 1;
                }
                else
                    return 0;
            } else {
                return -1;
            }
        }
    };

    Comparator<Record> compForSell = new Comparator<Record>() {
        public int compare(Record o1, Record o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return -1;
            } else if (o1.getPrice() .equals(o2.getPrice())) {
                if(o1.getTime().getTime()<o2.getTime().getTime()){
                    return -1;
                }
                else if(o1.getTime().getTime()>o2.getTime().getTime()){
                    return 1;

                }
                else
                    return 0;
            } else {
                return 1;
            }

        }


    };

    private List<Record> PQ2List(PriorityQueue<Record> p){
        List<Record> list =new ArrayList<Record>();
        for (Record record : p) {
            list.add(record);
        }
        return list;
    }

    private PriorityQueue<Record> List2PQ(List<Record> list,Comparator<Record> c){

        PriorityQueue<Record> p = new PriorityQueue<Record>(1,c);
        for(Record record:list){
            p.add(record);
        }
        return p;
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
    @Override
    public void setPrice(String code, Double price) {
        Jedis jedis = new Jedis();
        jedis.set(("Price"+code),price.toString());

    }

    @Override
    public Double getPrice(String code) {
        Jedis jedis = new Jedis();
        Double price = 0.0;
        if(jedis.exists("Price"+code)){
            price = Double.parseDouble(jedis.get("Price"+code));
        }
        return price;
    }

    @Override
    public PriceRecord getPriceRecord(String code) {
        Jedis jedis = new Jedis();
        PriceRecord p = new PriceRecord();
        p.setCode(code);
        if(jedis.exists("Price"+code)){
            p.setPrice( Double.parseDouble(jedis.get("Price"+code)));
            p.setTime(Timestamp.valueOf(jedis.get("Time"+code)));
            return p;
        }
        return null;
    }

    @Override
    public void setPriceRecord(PriceRecord p) {
        Jedis jedis = new Jedis();
        jedis.set(("Price"+p.getCode()),p.getPrice().toString());
        jedis.set(("Time"+p.getCode()),p.getTime().toString());
    }

}
