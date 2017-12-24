package com.module.service.serviceimpl;

import com.module.bean.Order;
import com.module.bean.Transaction;
import com.module.dao.OrderDAO;
import com.module.entity.OrderBook;

import com.module.entity.OrderQueue;
import com.module.entity.Record;
import com.module.jms.sender.Sender;
import com.module.service.OrderService;
import com.module.service.TransactionService;

import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.apache.commons.lang3.ObjectUtils.min;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private OrderBook orderBook;
    private String productCode;
    private TransactionService transactionService;
    private Sender sender;
    @Override
    public void update(Order or) {
        this.orderDAO.update(or);
    }

    @Override
    public void insert(Order or) {
        this.orderDAO.insert(or);
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public Order querybyid(Integer id) {
        return this.orderDAO.queryById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderDAO.getAllOrder();
    }



    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class})
    public int DealOrder(Order or)throws  Exception {
        RedisService redisService=new RedisServiceImpl();
        OrderBook orderBook=redisService.getOrderBook("Cu1706");
        orderDAO.insert(or);
        sender.sendOrder(or);
        int re=0;
        if(or.getType()==0){
            int gap=or.getNumber();
            Record top=orderBook.getSellBook().element();
            top.setLeft_number(top.getLeft_number()-gap);
            or.setLeft_number(or.getLeft_number()-gap);
            orderBook.getSellBook().remove();
            orderBook.getSellBook().add(top);
            redisService.setOrderBook(orderBook);
            Order ask=this.querybyid(top.getOrderid());
            Timestamp d = new Timestamp(System.currentTimeMillis());
            Transaction transaction = new Transaction(top.getPrice(), gap,ask, or, d,ask.getFirm().getName(),or.getFirm().getName());
            this.transactionService.insert(transaction);
            re=this.orderDAO.updateLeftNumber(top.getOrderid(),top.getLeft_number());
            if(re==-1)throw  new Exception();
            re=this.orderDAO.updateLeftNumber(or.getId(),or.getLeft_number());
            if(re==-1)throw  new Exception();
            this.sender.sendTransaction(transaction);
            this.sender.sendOrderBook(orderBook,top.getPrice(),d);
        }
        else if(or.getType()==1){

            Record top=orderBook.getSellBook().element();
            int gap=min(top.getLeft_number(),or.getLeft_number());
            orderBook.getSellBook().remove();
            or.setLeft_number(or.getLeft_number()-gap);
            re=this.orderDAO.updateLeftNumber(or.getId(),or.getLeft_number());
            if(re==-1)throw  new Exception();
            redisService.setOrderBook(orderBook);
            Order ask=this.querybyid(top.getOrderid());
            Timestamp d = new Timestamp(System.currentTimeMillis());
            Transaction transaction = new Transaction(top.getPrice(), gap,ask, or, d,ask.getFirm().getName(),or.getFirm().getName());
            this.transactionService.insert(transaction);
            this.sender.sendTransaction(transaction);
            top=orderBook.getSellBook().element();
            gap=min(or.getLeft_number(),top.getLeft_number());
            or.setLeft_number(or.getLeft_number()-gap);
            top.setLeft_number(top.getLeft_number()-gap);
            re=this.orderDAO.updateLeftNumber(or.getId(),or.getLeft_number());
            if(re==-1)throw  new Exception();
            re=this.orderDAO.updateLeftNumber(top.getOrderid(),top.getLeft_number());
            if(re==-1)throw  new Exception();
            ask=this.querybyid(top.getOrderid());
            d = new Timestamp(System.currentTimeMillis());
            orderBook.getSellBook().remove();
            orderBook.getSellBook().add(top);
            transaction = new Transaction(top.getPrice(), gap,ask, or, d,ask.getFirm().getName(),or.getFirm().getName());
            this.transactionService.insert(transaction);
            this.sender.sendTransaction(transaction);

            this.sender.sendOrderBook(orderBook,top.getPrice(),d);
        }
return 1;

    }
    public int compare(Record o1, Record o2) {
        if (o1.getPrice() < o2.getPrice()) {
            return -1;
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
            return 1;
        }

    }
    @Override
    public OrderBook dbToOrderBook() {
        List<Order>orders=this.orderDAO.getAllOrder();
        //Record r1=new Record(orders.get(4).getId(),orders.get(4).getGiven_price(),orders.get(4).getLeft_number(),orders.get(4).getTime());
        //Record r2=new Record(orders.get(5).getId(),orders.get(5).getGiven_price(),orders.get(5).getLeft_number(),orders.get(5).getTime());
        //if(compare(r1,r2)==1){
            //int a=0;
        //}
        OrderBook orderBook=new OrderBook();
        orderBook.setProductCode("Cu1706");
        for(int i=0;i<orders.size();++i){
            Record record=new Record(orders.get(i).getId(),orders.get(i).getGiven_price(),orders.get(i).getLeft_number(),orders.get(i).getTime());
            if(orders.get(i).getType_possession().equals("ask")){
                orderBook.getSellBook().add(record);
            }
            else{
                orderBook.getBuyBook().add(record);
            }
        }
        return orderBook;

    }
    private boolean judgeDeal(Record top,Order or){
        if(or.getType()==1) {
            return (or.getType_possession().equals("ask") && top.getPrice() >= or.getGiven_price()) || (or.getType_possession().equals("bid") && top.getPrice() <= or.getGiven_price());
        }
        else{
            return true;
        }
    }

    @Override
    public List<Order> getUnfinishedOrders() {
        List<Order> orders = orderDAO.getAllOrder();
        List<Order> result = new ArrayList<Order>();
        for(Order order:orders){
            if(order.getStatus().equals("canceled")||order.getLeft_number()==0){
                continue;
            }
            result.add(order);
        }
        return result;
    }
}
