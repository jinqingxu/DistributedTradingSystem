package com.module.bean;


import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class Order implements Serializable{
    private Integer id;
    private Double given_price;
    private Integer number;
    private Integer type;//market or limit or cancel 0 1 2
    private String type_possession;//sell or buy
    private Timestamp time;
    private Firm firm;
    private Product productCode;
    private Integer left_number;
    private String status;//finished unfinished canceled
    private Integer trorderid;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    public Integer getTrorderid() {
        return trorderid;
    }

    public void setTrorderid(Integer trorderid) {
        this.trorderid = trorderid;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
    }

    public Product getProductCode() {
        return productCode;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Double getGiven_price() {
        return given_price;
    }

    public Integer getLeft_number() {
        return left_number;
    }

    public void setGiven_price(Double given_price) {
        this.given_price = given_price;
    }

    public void setLeft_number(Integer left_number) {
        this.left_number = left_number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }



    public String getType_possession() {
        return type_possession;
    }

    public void setType_possession(String type_possession) {
        this.type_possession = type_possession;
    }




}
