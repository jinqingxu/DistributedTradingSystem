package com.module.dao;

import com.module.bean.Transaction;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/22.
 */
public interface TransactionDAO {
    public void insert(Transaction tr);
    public void delete(Transaction tr);
    public void update(Transaction tr);
    public List<Transaction> getAllTransaction();
    public Transaction queryById(Integer id);

    List<Transaction> getAllTransaction(int id);
    public double getDealNumberRate(Timestamp start, Timestamp end);

}
