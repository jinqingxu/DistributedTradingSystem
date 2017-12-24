package com.module.bean;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jinqingxu on 2017/6/2.
 */
public class CancelOrder implements Serializable{
    private Integer id;
    private Timestamp time;
    private Order cancelorder;
    private  Integer brokerorderid;

    public void setBrokerorderid(Integer brokerorderid) {
        this.brokerorderid = brokerorderid;
    }

    public Integer getBrokerorderid() {
        return brokerorderid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Order getCancelorder() {
        return cancelorder;
    }

    public void setCancelorder(Order cancelorder) {
        this.cancelorder = cancelorder;
    }
}
