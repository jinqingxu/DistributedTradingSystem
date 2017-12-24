package com.module.thread;

import com.module.bean.Firm;
import com.module.bean.Order;
import com.module.bean.Product;
import com.module.entity.OrderBook;
import com.module.entity.Record;
import com.module.service.ProductService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by jinqingxu on 2017/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations={"classpath:/applicationContext.xml",
})
public class ProcessNormalOrderThreadTest {
    @Resource
    private ProcessNormalOrderThread processNormalOrderThread;
    @Resource
    private ProductService productService;
    @Test
    public void execute() throws Exception {
        RedisService redisService=new RedisServiceImpl();
        //OrderBook orderBook=redisService.getOrderBook("Fe1806");
        OrderBook orderBook=redisService.getOrderBook("Fe1806");

        Order or2=processNormalOrderThread.getOrderService().querybyid(169);
        Record record=new Record(or2.getId(),or2.getGiven_price(),or2.getLeft_number(),or2.getTime());
        orderBook.AddSellBook(record);
        orderBook.RemoveSellBook(record);
        Order or3=processNormalOrderThread.getOrderService().querybyid(170);
        Order or4=processNormalOrderThread.getOrderService().querybyid(171);
        Record record1=new Record(or3.getId(),or3.getGiven_price(),or3.getLeft_number(),or3.getTime());
        Record record2=new Record(or4.getId(),or4.getGiven_price(),or4.getLeft_number(),or4.getTime());
        orderBook.AddBuyBook(record1);
        orderBook.AddBuyBook(record2);
        orderBook.RemoveBuyBook(record1);
        Product p=this.productService.queryByCode("Fe1806");
        Order or=new Order();
        or.setProductCode(p);
        Date date = new Date();
        Timestamp no = new Timestamp(date.getTime());
        or.setTime(no);
        or.setType(1);
        or.setType_possession("ask");
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
       this.processNormalOrderThread.execute(or);

    }

}