package com.module.action;

import com.module.bean.*;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.entity.Record;
import com.module.service.*;
import com.module.thread.ProcessLimitOrderThread;
import com.module.thread.ProcessNormalOrderThread;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import net.sf.json.JSONArray;
import websocketbot.BotEndpoint;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class DealOrderAction {
    private OrderService orderService;
   private TransactionService transactionService;
    private ProductService productService;
    private RedisService redisService;
    private FirmService firmService;
    private CancelOrderService cancelOrderService;

    private ProcessNormalOrderThread processNormalOrderThread;
    private ProcessLimitOrderThread processLimitOrderThread;

    public void setCancelOrderService(CancelOrderService cancelOrderService) {
        this.cancelOrderService = cancelOrderService;
    }

    public CancelOrderService getCancelOrderService() {
        return cancelOrderService;
    }

    public void setFirmService(FirmService firmService) {
        this.firmService = firmService;
    }

    public FirmService getFirmService() {
        return firmService;
    }

    public void setProcessLimitOrderThread(ProcessLimitOrderThread processLimitOrderThread) {
        this.processLimitOrderThread = processLimitOrderThread;
    }

    public ProcessLimitOrderThread getProcessLimitOrderThread() {
        return processLimitOrderThread;
    }

    public ProcessNormalOrderThread getProcessNormalOrderThread() {
        return processNormalOrderThread;
    }

    public void setProcessNormalOrderThread(ProcessNormalOrderThread processNormalOrderThread) {
        this.processNormalOrderThread = processNormalOrderThread;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ProductService getProductService() {
        return productService;
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
    public String testdao()throws Exception{
        //List<Order> orders=this.orderService.getOrderDAO().getAllOrder();
        //Order order=this.orderService.getOrderDAO().queryById(1);
        Order or=new Order();
        or.setStatus("finished");
        or.setLeft_number(10);
        or.setGiven_price(13.6);
        Firm firm=new Firm();
        firm.setName("jiaoda");
        firm.setIp("127.0.0.1");
        firm.setId(1);
        Firm f2=this.firmService.queryByName("jiaoda");
        or.setFirm(firm);
        or.setNumber(10);
        Product p=new Product();
        p.setCode("123456");
        p.setId(1);
        p.setProductName("iron11");
        p.setCategory1("metal");
        p.setCategory2("iron");
        Product p2=this.productService.queryByCode("123456");
       this.productService.insert(p);
        or.setProductCode(p);
        Date date = new Date();
        Timestamp no = new Timestamp(date.getTime());
        or.setTime(no);
        or.setType(1);
        or.setType_possession("bid");
        or.setTrorderid(1);
        //this.orderService.getOrderDAO().delete(or);
        this.orderService.getOrderDAO().insert(or);
        or.setType_possession("aid");
        this.orderService.getOrderDAO().update(or);
        this.orderService.getOrderDAO().updateStatus(or.getId(),"canceled");
        this.orderService.getOrderDAO().updateLeftNumber(or.getId(),1);
        //this.orderService.getOrderDAO().delete(or);
        List<Order> orders=this.orderService.getOrderDAO().getAllOrder();
        Order one=this.orderService.getOrderDAO().queryById(27);
        Order two=this.orderService.getOrderDAO().queryById(28);
        /*Transaction tr=new Transaction(13.6,10,one.getId(),two.getId(),no);
        transactionService.insert(tr);
        List<Transaction> transactions=transactionService.getAllTransaction();
        Transaction transaction=transactionService.queryById(1);
        tr.setPrice(15.6);
        transactionService.update(tr);
        transactionService.delete(tr);*/
        return "success";
    }
    Comparator<Record> compForBuy = new Comparator<Record>() {
        public int compare(Record o1, Record o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return 1;
            } else if (o1.getPrice() == o2.getPrice()) {
                if(o1.getTime().getTime()<o2.getTime().getTime()){
                    return 1;
                }
                else if(o1.getTime().getTime()>o2.getTime().getTime()){
                    return -1;
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
            } else if (o1.getPrice() == o2.getPrice()) {
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

    public String execute() throws Exception{
        /*Order or=new Order();
        or.setStatus("finished");
        or.setLeft_number(10);
        or.setGiven_price(13.6);
        Firm firm=new Firm();
        firm.setName("jiaoda");
        firm.setIp("127.0.0.1");
        firm.setId(1);
        Firm f2=this.firmService.queryByName("jiaoda");
        or.setFirm(firm);
        or.setNumber(10);
        Product p=new Product();
        p.setCode("123456");
        p.setId(1);
        p.setProductName("iron11");
        p.setCategory1("metal");
        p.setCategory2("iron");
        Product p2=this.productService.queryByCode("123456");
        this.productService.insert(p);
        or.setProductCode(p);
        Date date = new Date();
        Timestamp no = new Timestamp(date.getTime());
        or.setTime(no);
        or.setType(1);
        or.setType_possession("bid");
        or.setTrorderid(1);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        CancelOrder cor=new CancelOrder();
        cor.setCancelorder(or);
        cor.setTime(d);
        cor.setTraderorderid(5);
        this.cancelOrderService.DealOrder(cor,or.getProductCode().getCode());
        /*List<Order> orders=this.orderService.getAllOrders();
        OrderBook orderBook=new OrderBook();
        orderBook.setBrokerId(1);
        orderBook.setProductCode("123456");
        PriorityQueue<Record> sellbook=new PriorityQueue<Record>(10,compForSell);
        PriorityQueue<Record> buybook=new PriorityQueue<Record>(10,compForBuy);
        orderBook.setBuyBook(buybook);
        orderBook.setSellBook(sellbook);
        for(int i=0;i<orders.size();++i){
            Order or=orders.get(i);
            if(or.getType_possession().equals("ask")){
                orderBook.AddSellBook((new Record(or.getId(),or.getGiven_price(),or.getLeft_number(),or.getTime())));
            }
            else{
                orderBook.AddBuyBook(new Record(or.getId(),or.getGiven_price(),or.getLeft_number(),or.getTime()));
            }

        }

        this.redisService.setOrderBook(orderBook);*/
        /*Product p=new Product();
        p.setCode("123456");
        p.setId(1);
        p.setProductName("iron11");
        p.setCategory1("metal");
        p.setCategory2("iron");
        Order or=new Order();
        or.setProductCode(p);
        Date date = new Date();
        Timestamp no = new Timestamp(date.getTime());
        or.setTime(no);
        or.setType(1);
        or.setType_possession("bid");
        or.setTrorderid(6);
        or.setGiven_price(13.6);
        or.setLeft_number(10);
        or.setNumber(10);
        or.setStatus("unfinished");
        Firm firm=new Firm();
        firm.setName("jiaoda");
        firm.setIp("127.0.0.1");
        firm.setId(1);
        or.setFirm(firm);
        or.setTrorderid(7);
        processNormalOrderThread.execute(or);*/
        BotEndpoint botEndpoint=new BotEndpoint();
        RedisService redisService=new RedisServiceImpl();
        OrderBook orderBook=redisService.getOrderBook("Cu1706");
        List<DisRecord> buy=new ArrayList<DisRecord>();
        List<DisRecord> sell=new ArrayList<DisRecord>();
        buy.addAll(orderBook.getDisBuyBook());
        buy.remove(0);
        buy.remove(1);
        sell.addAll(orderBook.getDisSellBook());
        JSONArray jsonsell= JSONArray.fromObject(sell);
        JSONArray jsonbuy=JSONArray.fromObject(buy);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        botEndpoint.sendAll(jsonbuy.toString(),jsonsell.toString(),"Cu1706",111.0,d);


        return "success";
    }

}
