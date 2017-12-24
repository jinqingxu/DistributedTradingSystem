package com.module.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by siqi.lou on 2017/6/5.
 */
public class PriceRecord implements Serializable {
    int id;
    Double price;
    String code;
    String brokerName;
    Timestamp time;

    public PriceRecord(Double price, String code, String brokerName, Timestamp time) {
        this.price = price;
        this.code = code;
        this.brokerName = brokerName;
        this.time = time;
    }

    public PriceRecord(Double price, String code, String brokerName, String time) {
        this.price = price;
        this.code = code;
        this.brokerName = brokerName;
        this.time = Timestamp.valueOf(time);
    }

    public PriceRecord() {
    }

    @Override
    public String toString() {
        return "PriceRecord{" +
                "price=" + price +
                ", code='" + code + '\'' +
                ", brokerName='" + brokerName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}