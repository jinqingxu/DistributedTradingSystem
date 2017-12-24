package RedisTest;

import com.module.bean.Order;
import com.module.entity.OrderBook;
import com.module.entity.OrderQueue;
import com.module.entity.Record;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import com.module.util.RedisUtil;
import com.sun.prism.impl.Disposer;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

/**
 * Created by siqi.lou on 21/05/2017.
 */
public class RedisServiceTest {
    @Resource
    private RedisService redisService;


    @Before
    public void setup(){
        redisService = new RedisServiceImpl();
    }

    @Test
    public void getOrderBook() throws Exception {
        redisService.getOrderBook("Fe1809");

    }

    @Test
    public void setOrderBook() throws Exception {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Record r =new Record(1,2.5,3,time);
        OrderBook orderBook = new OrderBook();
        PriorityQueue<Record> r1 = new PriorityQueue<Record>();
        r1.add(r);
        orderBook.setSellBook(r1);
        orderBook.setBuyBook(r1);
        orderBook.setProductCode("Fe1809");
        redisService.setOrderBook(orderBook);

    }


    @Test
    public void setRequestQueue() throws Exception {
        String code = "Fe1806";
        OrderQueue orderQueue = new OrderQueue();
        PriorityQueue<Order> p = new PriorityQueue<Order>();
        Order order = new Order();
        order.setGiven_price(943.2);
        order.setId(2);
        p.add(order);
        orderQueue.setOrderQueue(p);
        orderQueue.setStatus(0);
        redisService.setRequestQueue(code,orderQueue);
    }


    @Test
    public void setLimitOrderQueue() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order();
        order.setType(1);
        orderQueue.getOrderQueue().add(order);
        orderQueue.setStatus(1);
        redisService.setLimitOrderQueue("Fe1809",orderQueue);
        redisService.getLimitOrderQueue("Fe1809");

    }
}