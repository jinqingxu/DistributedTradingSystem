package com.module.entity;

import net.sf.json.JSONArray;

import java.util.*;


/**
 * Created by jinqingxu on 2017/5/20.
 */

public class OrderBook {
    private  Integer brokerId=-1;
    private  Integer productId=-1;
    private String productCode="";

    Comparator<Record> compForBuy = new Comparator<Record>() {
        public int compare(Record o1, Record o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return 1;
            } else if (o1.getPrice() .equals(o2.getPrice())) {
                if(o1.getTime().getTime()<o2.getTime().getTime()){
                    return -1;
                }
                else if(o1.getTime().getTime()>o2.getTime().getTime()){
                    return 1;
                }
                else
                    return 0;
            } else {
                return -1;
            }
        }
    };

    Comparator<Record> compForSell = new Comparator<Record>() {
        public int compare(Record o1, Record o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return -1;
            } else if (o1.getPrice().equals(o2.getPrice())) {
                if(o1.getTime().getTime()<o2.getTime().getTime()){
                    return -1;
                }
                else if(o1.getTime().getTime()>o2.getTime().getTime()){
                    return 1;

                }
                else
                    return 0;
            } else {
                return 1;
            }

        }


    };
    Comparator<DisRecord> compForDisBuy = new Comparator<DisRecord>() {
        public int compare(DisRecord o1, DisRecord o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return 1;
            } else if (o1.getPrice() .equals(o2.getPrice())) {
                    return 0;
            } else {
                return -1;
            }
        }
    };

    Comparator<DisRecord> compForDisSell = new Comparator<DisRecord>() {
        public int compare(DisRecord o1, DisRecord o2) {
            if (o1.getPrice() < o2.getPrice()) {
                return -1;
            } else if (o1.getPrice() .equals(o2.getPrice())) {
                    return 0;
            } else {
                return 1;
            }

        }


    };
    private  PriorityQueue<Record>sellBook=new PriorityQueue<Record>(100,compForSell);
    private  PriorityQueue<Record>buyBook=new PriorityQueue<Record>(100,compForBuy);
    private PriorityQueue<DisRecord> getDisBook(PriorityQueue<Record> tbook,String type){
        PriorityQueue<DisRecord> re=new PriorityQueue<DisRecord>();
        PriorityQueue<Record> tq=null;
        if(type.equals("sell")){
            tq=new PriorityQueue<Record>(100,compForSell);
            re=new PriorityQueue<DisRecord>(100,compForDisSell);
        }
        else{
            tq=new PriorityQueue<Record>(100,compForBuy);
            re=new PriorityQueue<DisRecord>(100,compForDisBuy);
        }

        while(tbook.size()!=0){
            Record record=tbook.element();
            tq.add(tbook.element());
            tbook.remove();
        }
        Record r=null;
        Integer sumnumber=0;
        Double curprice=0.0;
        DisRecord disRecord=null;

        while(tq.size()!=0){
            sumnumber+=tq.element().getLeft_number();
            curprice=tq.element().getPrice();
            tq.remove();
            while(tq.size()!=0){
                if(tq.element().getPrice().equals(curprice)){
                    sumnumber+=tq.element().getLeft_number();
                    tq.remove();
                }
                else{
                    break;
                }
            }
            disRecord=new DisRecord();
            disRecord.setNumber(sumnumber);
            disRecord.setPrice(curprice);
            re.add(disRecord);
            curprice=0.0;
            sumnumber=0;
        }
        return re;
    }
    public PriorityQueue<DisRecord> getDisSellBook(){
        return getDisBook(sellBook,"sell");
    }
    public PriorityQueue<DisRecord> getDisBuyBook(){
      return getDisBook(buyBook,"buy");
    }
    public PriorityQueue<Record> getSellBook() {
        return sellBook;
    }

    public PriorityQueue<Record> getBuyBook() {
        return buyBook;
    }

    public void setBuyBook(PriorityQueue<Record> buyBook) {
        this.buyBook = buyBook;
    }

    public void setSellBook(PriorityQueue<Record> sellBook) {
        this.sellBook = sellBook;
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
    public void AddSellBook(Record r){
        this.sellBook.add(r);
    }
    public void RemoveSellBook(Record r){

        this.sellBook.remove(r);
    }
    public void AddBuyBook(Record r){
        this.buyBook.add(r);
    }
    public void RemoveBuyBook(Record r){

        this.buyBook.remove(r);
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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
