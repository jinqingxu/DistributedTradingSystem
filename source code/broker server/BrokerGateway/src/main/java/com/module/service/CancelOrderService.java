package com.module.service;

import com.module.bean.CancelOrder;
import com.module.bean.Order;

/**
 * Created by jinqingxu on 2017/6/2.
 */
public interface CancelOrderService {
    public void insert(CancelOrder cancelOrder);
    public int DealOrder(CancelOrder cancelOrder, String productCode)throws Exception;

}
