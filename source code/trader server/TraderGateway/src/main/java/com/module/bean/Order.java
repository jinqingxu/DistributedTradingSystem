package com.module.bean;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class Order implements Serializable {
    private Integer id;
    private Double given_price;
    private String status;
    private Integer left_number;
    private Timestamp time;
    private Broker broker;
    private Product product;
    private Trader trader;
    private Integer brokerorderid;
    //possession: ask/bid
    private String possession;
    private Integer number;
    // 0-market 1-limit 2-cancel 3-stop
    private Integer type;
    private Integer originid;

    public Integer getOriginid() {
        return originid;
    }

    public void setOriginid(Integer originid) {
        this.originid = originid;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBrokerorderid() {
        return brokerorderid;
    }

    public void setBrokerorderid(Integer brokerorderid) {
        this.brokerorderid = brokerorderid;
    }

    public Integer getType() {
        return type;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public String getPossession() {
        return possession;
    }

    public void setPossession(String possession) {
        this.possession = possession;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getLeft_number() {
        return left_number;
    }

    public void setLeft_number(Integer left_number) {
        this.left_number = left_number;
    }

    public String getStatus() {
        return status;
    }

    public void setGiven_price(Double given_price) {
        this.given_price = given_price;
    }

    public Double getGiven_price() {
        return given_price;
    }

    public Broker getBroker() {
        return broker;
    }


    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }



    public void setTrader(Trader trader) {
        this.trader = trader;
    }
}
