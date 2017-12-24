package com.module.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jinqingxu on 2017/5/21.
 */
public class Product implements Serializable{
    private Integer id;
    private String code;
    private Timestamp listingDate;
    private Timestamp deliveryDate;
    private String productName;
    private String category1;
    private String category2;

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory1() {
        return category1;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getProductName() {
        return productName;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public Timestamp getListingDate() {
        return listingDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setListingDate(Timestamp listingDate) {
        this.listingDate = listingDate;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


}
