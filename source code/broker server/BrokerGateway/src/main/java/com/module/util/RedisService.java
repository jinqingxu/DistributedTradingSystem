package com.module.util;

import com.module.bean.PriceRecord;
import com.module.entity.CancelOrderQueue;
import com.module.entity.OrderBook;
import com.module.entity.OrderQueue;
import org.springframework.stereotype.Component;

/**
 * Created by siqi.lou on 21/05/2017.
 */
@Component
public interface RedisService {

    //从redis里读出某个商品的交易委托账本
    //OrderBook getOrderBook(int productId);

    OrderBook getOrderBook(String productCode);
    //OrderBook getLimitOrderBook(String productCode,int number);
    void setOrderBook(OrderBook orderBook);

    CancelOrderQueue getCancelOrderQueue(String productCode);
    void setCancelOrderQueue(String productCode,CancelOrderQueue cancelOrderQueue);

    OrderQueue getRequestQueue(String productCode);
    void setRequestQueue(String code, OrderQueue orderQueue);

    OrderQueue getLimitOrderQueue(String code);
    void setLimitOrderQueue(String code,OrderQueue orderQueue);

    void setPrice(String code,Double price);
    Double getPrice(String code);

    PriceRecord getPriceRecord(String code);
    void setPriceRecord(PriceRecord p);

}
