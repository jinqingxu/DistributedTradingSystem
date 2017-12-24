package com.module.service.serviceimpl;

import com.module.bean.CancelOrder;
import com.module.bean.Order;
import com.module.dao.CancelOrderDAO;
import com.module.dao.OrderDAO;
import com.module.entity.OrderBook;
import com.module.entity.Record;
import com.module.jms.sender.Sender;
import com.module.service.CancelOrderService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jinqingxu on 2017/6/2.
 */
public class CancelOrderServiceImpl implements CancelOrderService {
    private CancelOrderDAO cancelOrderDAO;
    private OrderDAO orderDao;
    private RedisService redisService;
    private Sender sender;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public CancelOrderDAO getCancelOrderDAO() {
        return cancelOrderDAO;
    }

    public OrderDAO getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDAO orderDao) {
        this.orderDao = orderDao;
    }

    public void setCancelOrderDAO(CancelOrderDAO cancelOrderDAO) {
        this.cancelOrderDAO = cancelOrderDAO;
    }
    public void insert(CancelOrder cancelOrder){
        this.cancelOrderDAO.insert(cancelOrder);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class})
    public int DealOrder(CancelOrder cancelOrder, String productCode)throws Exception {
        OrderBook orderBook=redisService.getOrderBook(productCode);
        Order or=cancelOrder.getCancelorder();
        String status=null;
        //can not been canceled
        if(or.getLeft_number()==0){
            return 0;
        }
        status="canceled";
        int re=orderDao.updateStatus(or.getId(),status);
        cancelOrder.setCancelorder(or);
        if(re==-1){
            throw new Exception("can not be canceled");
        }
        if(or.getType_possession().equals("sell")){
            orderBook.RemoveSellBook(new Record(or.getId(),or.getGiven_price(),or.getLeft_number(),or.getTime()));
        }
        else{
            orderBook.RemoveBuyBook(new Record(or.getId(),or.getGiven_price(),or.getLeft_number(),or.getTime()));
        }
        redisService.setOrderBook(orderBook);
        this.sender.sendOrderBook(orderBook);
        sender.sendCancelOrder(cancelOrder.getCancelorder());
        return 1;
    }


    /*public void display(CancelOrder cancelOrder) {
        this.orderDao.updateStatus(cancelOrder.getCancelorder().getId(),"canceled");
        RedisService redisService=new RedisServiceImpl();
        OrderBook orderBook=redisService.getOrderBook("Cu1706");
        orderBook.getSellBook().remove();
        redisService.setOrderBook(orderBook);
        this.sender.sendOrderBook(orderBook);
        sender.sendCancelOrder(cancelOrder.getCancelorder());
    }*/
}
