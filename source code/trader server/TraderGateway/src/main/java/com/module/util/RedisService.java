package com.module.util;


import com.module.bean.Order;

import com.module.bean.PriceRecord;
import com.module.entity.OrderBook;
import com.module.entity.OrderQueue;
import org.springframework.stereotype.Component;

import java.util.PriorityQueue;

/**
 * Created by siqi.lou on 21/05/2017.
 */
@Component
public interface RedisService {
    void setStopOrderQueue(String code,String broker,OrderQueue q);
    OrderQueue getStopOrderQueue(String code,String broker);


    //void setOrderBook(String code,String firm);

    //public OrderQueue getStopOrderQueue(String code);
    //public void setStopOrderQueue(String code,OrderQueue orderQueue);

    OrderBook getOrderBook(String productCode,String broker);
    void setOrderBook(OrderBook orderBook);

    void setPrice(String code,String broker,Double price);
    Double getPrice(String code,String broker);

    PriceRecord getPriceRecord(String code,String broker);
    void setPriceRecord(PriceRecord p);

}
