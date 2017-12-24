package com.module.action;

import com.module.bean.*;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.service.*;
import com.module.thread.ProcessStopOrderThread;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import com.opensymphony.oscache.web.filter.CacheHttpServletResponseWrapper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import  java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.hibernate.SessionFactory;
import websocketbot.BotEndpoint;
import websocketbot.messages.BuyBookMessage;

import java.util.List;

public class TestDaoAction extends ActionSupport {

    private BrokerService brokerService;
    private OrderService orderService;
    private ProductService productService;
    private TraderService traderService;
    private TransactionService transactionService;
    private CancelOrderService cancelOrderService;
    private ProcessStopOrderThread processStopOrderThread;

    public ProcessStopOrderThread getProcessStopOrderThread() {
        return processStopOrderThread;
    }

    public void setProcessStopOrderThread(ProcessStopOrderThread processStopOrderThread) {
        this.processStopOrderThread = processStopOrderThread;
    }

    public CancelOrderService getCancelOrderService() {
        return cancelOrderService;
    }

    public void setCancelOrderService(CancelOrderService cancelOrderService) {
        this.cancelOrderService = cancelOrderService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public BrokerService getBrokerService() {
        return brokerService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public TraderService getTraderService() {
        return traderService;
    }

    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setTraderService(TraderService traderService) {
        this.traderService = traderService;
    }

    public String execute() throws Exception {
        /*Broker broker=new Broker();
        broker.setName("xuxu");
        broker.setIp("126.7.0.1");
        brokerService.insert(broker);
        List<Broker> brokers=this.brokerService.getAllBrokers();
        broker.setName("qingqing");
        this.brokerService.update(broker);
        //this.brokerService.delete(broker);
        //trader
        Trader trader=new Trader();
        trader.setUsername("jinjin");
        trader.setPassword("123");
        traderService.insert(trader);
        List<Trader> traders=traderService.getAllTraders();
        trader.setUsername("qq");
        traderService.update(trader);
        //traderService.delete(trader);
        //product
        Product product=new Product();
        product.setCode("234");
        Timestamp d = new Timestamp(System.currentTimeMillis());
        product.setDeliveryDate(d);
        product.setListingDate(d);
        product.setProductName("iron11");
        product.setCategory1("metal");
        product.setCategory2("iron");
        productService.insert(product);
        List<Product> products=productService.getAllProducts();
        product.setProductName("iron12");
        productService.update(product);
        //productService.delete(product);
        //order
        Order order=new Order();
        order.setType(1);
        order.setTime(d);
        order.setNumber(10);
        order.setGiven_price(12.5);
        order.setLeft_number(10);
        order.setStatus("unfinished");
        order.setBroker(broker);
        order.setTrader(trader);
        order.setPossession("ask");
        order.setProduct(product);
        order.setBrokerorderid(27);
        this.orderService.insert(order);
        List<Order> orders=this.orderService.getAllOrder();
        order.setBrokerorderid(28);
        this.orderService.update(order);
        //this.orderService.delete(order);
        //transaction
        Transaction transaction=new Transaction(13.6,10,order,"jiaoda",d);
        this.transactionService.insert(transaction);
        List<Transaction> transactions=this.transactionService.getAllTransaction();
        transaction.setNumber(100);
        this.transactionService.update(transaction);
        this.transactionService.delete(transaction);
        return "success";*/
       /* Order or=this.orderService.queryById(4);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        /*CancelOrder cor=new CancelOrder();
        cor.setCancelorder(or);
        cor.setTime(d);
        cor.setBrokerorderid(5);
        this.cancelOrderService.placeOrder(cor);
        or.setType(2);
        this.orderService.update(or);*/
        //processStopOrderThread.execute("123456");
        //List<String>c1=this.productService.getCategoryList(0,"");
        //List<String>c2=this.productService.getCategoryList(1,"metal");

        /*List<Product>p2=this.productService.queryByCategory2("iron");
        Product p=this.productService.queryByCode("234");
        List<Order> uOrders=this.orderService.getAllUnfinishedOrder();
        List<Order> uOrders1=this.orderService.getAllUnfinishedOrder(13);*/

        //test websocket
        BotEndpoint botEndpoint=new BotEndpoint();
        List<DisRecord> l=new ArrayList<DisRecord>();
        List<DisRecord> l2=new ArrayList<DisRecord>();
        for(int i=10;i<20;++i){
            DisRecord disRecord=new DisRecord(i+0.1,i);
            l.add(disRecord);
        }
        for(int i=20;i<30;++i){
            DisRecord disRecord=new DisRecord(i+0.1,i);
            l2.add(disRecord);

        }
        OrderBook orderBook=new OrderBook();
        orderBook.setProductCode("Fe1806");
        orderBook.setBuyBook(l);
        orderBook.setSellBook(l2);
        orderBook.setBrokerId(101);
        RedisService redisService=new RedisServiceImpl();
        redisService.setOrderBook(orderBook);
        botEndpoint.sendAll(orderBook,1.6);
        return null;
    }
}
