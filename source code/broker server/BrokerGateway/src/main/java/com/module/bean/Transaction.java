package com.module.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 47 on 2017/5/20.
 */
public class Transaction implements Serializable {
    private int id;
    private double price;
    private int number;
    private Order askorder;
    private Order bidorder;
    private Timestamp time;
    private String askFirm;
    private String bidFirm;

    public String getAskFirm() {
        return askFirm;
    }

    public String getBidFirm() {
        return bidFirm;
    }

    public void setAskFirm(String askFirm) {
        this.askFirm = askFirm;
    }

    public void setBidFirm(String bidFirm) {
        this.bidFirm = bidFirm;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public Order getAskorder() {
        return askorder;
    }

    public Order getBidorder() {
        return bidorder;
    }

    public void setAskorder(Order askorder) {
        this.askorder = askorder;
    }

    public void setBidorder(Order bidorder) {
        this.bidorder = bidorder;
    }
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public Transaction(double price,int number,Order askorder,Order bidorder,Timestamp time,String askFirm,String bidFirm){

        this.askorder=askorder;
        this.bidorder=bidorder;
        this.price=price;
        this.number=number;
        this.time=time;
        this.askFirm=askFirm;
        this.bidFirm=bidFirm;
    }

    public Transaction(){}

}
