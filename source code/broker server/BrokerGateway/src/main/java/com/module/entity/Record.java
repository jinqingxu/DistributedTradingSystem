package com.module.entity;


import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Created by jinqingxu on 2017/5/20.
 */
public class Record implements Serializable{
    private Integer orderid;
    private Double price;
    private Integer left_number;

    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setLeft_number(Integer left_number) {
        this.left_number = left_number;
    }

    public Integer getLeft_number() {
        return left_number;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Record(Integer orderid, Double price,Integer left_number,Timestamp time){
        this.orderid=orderid;
        this.price=price;
        this.left_number=left_number;
        this.time=time;
    };

    @Override
    public String toString() {
        return "Record{" +
                "orderid=" + orderid +
                ", price=" + price +
                ", left_number=" + left_number +
                '}';
    }

}
