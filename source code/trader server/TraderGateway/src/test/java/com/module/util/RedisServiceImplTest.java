package com.module.util;

import com.module.bean.Order;
import com.module.entity.OrderQueue;
import com.module.bean.PriceRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.PriorityQueue;

/**
 * Created by siqi.lou on 02/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class RedisServiceImplTest {

    //@Resource
    //private MongoTemplate mongoTemplate;

    @Test
    public void setStopOrderQueue() throws Exception {

        OrderQueue orderQueue = new OrderQueue();
        Order order =  new Order();
        order.setNumber(1);
        PriorityQueue<Order> p = new PriorityQueue<Order>();
        p.add(order);
        orderQueue.setOrderQueue(p);
        orderQueue.setStatus(1);

        RedisService redisService = new RedisServiceImpl();
        redisService.setStopOrderQueue("fe1019","xxx",orderQueue);
    }

    @Test
    public void getStopOrderQueue() throws Exception {

        RedisService redisService = new RedisServiceImpl();
        redisService.getStopOrderQueue("fe1019","xxx");
    }

    @Test
    public void TestMongoDb() throws Exception{
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PriceRecord p = new PriceRecord(12.5,"Fe1909","broker",timestamp.toString());
        //mongoTemplate.insert(p);
        //Query query = new Query(Criteria.where("brokerName").is("broker"));
        //PriceRecord x = mongoTemplate.findOne(query,PriceRecord.class);
    }


}