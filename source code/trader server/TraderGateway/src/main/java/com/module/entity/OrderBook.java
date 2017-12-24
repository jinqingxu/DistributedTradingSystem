package com.module.entity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;


/**
 * Created by jinqingxu on 2017/5/20.
 */

public class OrderBook {
    private  Integer brokerId=-1;
    private  Integer productId=-1;
    private String productCode;
    private String brokerName;
    private List<DisRecord> sellBook;
    private List<DisRecord> buyBook;
    private Comparator<DisRecord> compForBuy = new Comparator<DisRecord>() {
        public int compare(DisRecord o1, DisRecord o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return 1;
            } else if (o1.getPrice() == o2.getPrice()) {
                    return 0;
            } else {
                return -1;
            }
        }
    };

    private Comparator<DisRecord> compForSell = new Comparator<DisRecord>() {
        public int compare(DisRecord o1, DisRecord o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return -1;
            } else if (o1.getPrice() == o2.getPrice()) {
                    return 0;
            } else {
                return 1;
            }

        }
    };

    public void setCompForSell(Comparator<DisRecord> compForSell) {
        this.compForSell = compForSell;
    }

    public void setCompForBuy(Comparator<DisRecord> compForBuy) {
        this.compForBuy = compForBuy;
    }

    public Comparator<DisRecord> getCompForSell() {
        return compForSell;
    }

    public Comparator<DisRecord> getCompForBuy() {
        return compForBuy;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<DisRecord> getSellBook() {
        return sellBook;
    }

    public void setSellBook(List<DisRecord> sellBook) {
        this.sellBook = sellBook;
    }

    public List<DisRecord> getBuyBook() {
        return buyBook;
    }

    public void setBuyBook(List<DisRecord> buyBook) {
        this.buyBook = buyBook;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }
    public String buybookToString(){
        JSONArray jsonArray= JSONArray.fromObject(this.buyBook);
        return jsonArray.toString();
    }
    public String sellbookToString(){
        JSONArray jsonArray= JSONArray.fromObject(this.sellBook);
        return jsonArray.toString();
    }

}
