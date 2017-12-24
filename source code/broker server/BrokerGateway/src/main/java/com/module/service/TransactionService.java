package com.module.service;

import com.module.bean.Firm;
import com.module.bean.Transaction;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/22.
 */
public interface TransactionService {
    public void insert(Transaction tr);
    public void delete(Transaction tr);
    public void update(Transaction tr);
    public List<Transaction> getAllTransaction();
    public Transaction queryById(Integer id);


    public Firm getAskFirm(Transaction tr);
    public Firm getBidFirm(Transaction tr);

    //to checkout is this firm in transaction
    public Boolean inTrans(Transaction tr,Firm f);

    public List<Transaction> getProductTransaction(String code);

}
