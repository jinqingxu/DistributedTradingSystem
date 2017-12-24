package com.module.service.serviceimpl;

import com.module.bean.CancelOrder;
import com.module.dao.CancelOrderDAO;
import com.module.service.CancelOrderService;

/**
 * Created by jinqingxu on 2017/6/2.
 */
public class CancelOrderServiceImpl implements CancelOrderService {
    private CancelOrderDAO cancelOrderDAO;

    public CancelOrderDAO getCancelOrderDAO() {
        return cancelOrderDAO;
    }

    public void setCancelOrderDAO(CancelOrderDAO cancelOrderDAO) {
        this.cancelOrderDAO = cancelOrderDAO;
    }
    public void placeOrder(CancelOrder cancelOrder){
        this.cancelOrderDAO.insert(cancelOrder);
    }
}
