package com.module.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 47 on 2017/5/20.
 */
public class Transaction implements Serializable{
    private int id;
    private double price;
    private int number;
    private Order myorder;
    //private Order bidorder;
    private String firmname;
    private Timestamp time;

    public void setFirmname(String firmname) {
        this.firmname = firmname;
    }

    public String getFirmname() {
        return firmname;
    }

    public void setMyorder(Order myorder) {
        this.myorder = myorder;
    }

    public Order getMyorder() {
        return myorder;
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


    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public Transaction(double price,int number,Order myorder,String firmname,Timestamp time){
        this.myorder=myorder;
        this.firmname = firmname;
        this.price=price;
        this.number=number;
        this.time=time;
    }
    public Transaction(){}
}
