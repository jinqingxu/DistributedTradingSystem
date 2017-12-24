package com.module.entity;

import java.io.Serializable;

/**
 * Created by siqi.lou on 30/05/2017.
 */
public class DisRecord implements Serializable{
    private Double price;
    private int number;

    public Double getPrice() {
        return price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public DisRecord(Double price,int number){
        this.price=price;
        this.number=number;

    }

    public DisRecord(){};
}
