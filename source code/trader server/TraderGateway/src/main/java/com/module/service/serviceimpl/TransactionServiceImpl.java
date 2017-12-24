package com.module.service.serviceimpl;


import com.module.bean.Order;
import com.module.bean.Transaction;
import com.module.dao.OrderDAO;
import com.module.dao.TransactionDAO;
import com.module.service.TransactionService;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/22.
 */
public class TransactionServiceImpl implements TransactionService {
    private TransactionDAO transactionDAO;
    private OrderDAO orderDAO;
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }

    public void insert(Transaction tr){
        this.transactionDAO.insert(tr);
    };
    public void delete(Transaction tr){
        this.transactionDAO.delete(tr);
    };
    public void update(Transaction tr){
        this.transactionDAO.update(tr);
    };
    public List<Transaction> getAllTransaction(){
        return this.transactionDAO.getAllTransaction();
    };
    public Transaction queryById(Integer id){
        return this.transactionDAO.queryById(id);
    };

    @Override
    public List<Transaction> getAllTransaction(int traderId) {
        return transactionDAO.getAllTransaction(traderId);
    }
    @Override
    public double getDealNumberRate(Timestamp start, Timestamp end) {
        return this.transactionDAO.getDealNumberRate(start,end);
    }
}
