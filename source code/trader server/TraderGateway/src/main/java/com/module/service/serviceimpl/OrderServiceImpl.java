package com.module.service.serviceimpl;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.bean.Transaction;
import com.module.dao.CancelOrderDAO;
import com.module.dao.OrderDAO;
import com.module.entity.OrderQueue;
import com.module.service.OrderService;
import com.module.service.TransactionService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Queue;

import static org.apache.commons.lang3.ObjectUtils.min;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private RedisService redisService;
    private TransactionService transactionService;

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public List<Order> getAllOrder(){
        return this.orderDAO.getAllOrder();
    }
    public void update(Order or){
        this.orderDAO.update(or);
    }
    public void updateStatus(Integer id,String status){
        this.orderDAO.updateStatus(id,status);
    }
    public void updateLeftNumber(Integer id,Integer leftnumber){
        this.orderDAO.updateLeftNumber(id,leftnumber);
    }
    public Order queryById(Integer id){
        return this.orderDAO.queryById(id);
    }
    public void insert(Order or){
        this.orderDAO.insert(or);
    }
    public void delete(Order or){
        this.orderDAO.delete(or);
    }

    @Override
    public List<Order> getAllUnfinishedOrder() {
        return this.orderDAO.getAllUnfinishedOrder();
    }

    @Override
    public void orderReceived(int id, int brokerId) {
       Order myorder=this.orderDAO.queryById(id);
       myorder.setBrokerorderid(brokerId);
       this.orderDAO.update(myorder);
    }

    @Override
    public List<Order> getAllOrder(int id) {
        return orderDAO.getAllOrder(id);
    }

    public void placeOrder(Order or) {
             this.orderDAO.insert(or);
             if(or.getType()==3){
                 OrderQueue orderQueue=this.redisService.getStopOrderQueue(or.getProduct().getCode(),or.getBroker().getName());
                 Queue<Order> q=orderQueue.getOrderQueue();
                 q.add(or);
                 this.redisService.setStopOrderQueue(or.getProduct().getCode(),or.getBroker().getName(),orderQueue);
             }
    }

    @Override
    public List<Order> getAllUnfinishedOrder(Integer id) {
        return this.orderDAO.getAllUnfinishedOrder(id);
    }
    @Override
    public void vamp()throws Exception {
        List<Order> orders=this.orderDAO.getVampOrder();
        long length=8*60*60;//开市8小时
        for(int i=0;i<orders.size();++i){
            Timestamp d=new Timestamp(System.currentTimeMillis());
            if(orders.get(i).getTime().getTime()>=d.getTime()-length){
                Timestamp start=new Timestamp(d.getTime()-2*60);//两分钟为时间窗
                double propotion=this.transactionService.getDealNumberRate(start,d);
                int number=(int)Math.round(propotion*orders.get(i).getNumber());
                Order origin=orders.get(i);
                Order or=new Order();
                or.setTrader(origin.getTrader());
                or.setTime(origin.getTime());
                or.setOriginid(origin.getId());
                or.setPossession(origin.getPossession());
                or.setStatus(origin.getStatus());
                or.setType(0);
                or.setNumber(number);
                or.setLeft_number(number);
                or.setBrokerorderid(origin.getBrokerorderid());
                or.setBroker(origin.getBroker());
                or.setProduct(origin.getProduct());

            }
        }

    }

}
