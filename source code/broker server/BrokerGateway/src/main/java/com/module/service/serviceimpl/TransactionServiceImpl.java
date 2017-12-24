package com.module.service.serviceimpl;

import com.module.bean.Firm;
import com.module.bean.Order;
import com.module.bean.Product;
import com.module.bean.Transaction;
import com.module.dao.FirmDAO;
import com.module.dao.OrderDAO;
import com.module.dao.ProductDAO;
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
    private FirmDAO firmDAO;
    private ProductDAO productDAO;
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }
    public void setFirmDAO(FirmDAO firmDAO) {
        this.firmDAO = firmDAO;
    }

    public FirmDAO getFirmDAO() {
        return firmDAO;
    }

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
    private Firm getFirm(Integer orderid){
        Order or=orderDAO.queryById(orderid);
        Integer firmid=or.getFirm().getId();
        return firmDAO.queryById(firmid);
    }
    @Override
    public Firm getAskFirm(Transaction tr) {
       Integer orderid=tr.getAskorder().getId();
       return getFirm(orderid);
    }

    @Override
    public Firm getBidFirm(Transaction tr) {
        Integer orderid=tr.getBidorder().getId();
        return getFirm(orderid);
    }

    //TODO
    // check if firm f in the trade of specific transaction tr
    @Override
    public Boolean inTrans(Transaction tr, Firm f) {
       String firmname1=tr.getAskFirm();
       String firmname2=tr.getBidFirm();
       if(f.getName().equals(firmname1)||f.getName().equals(firmname2)){
           return true;
       }
       else{
           return false;
       }

    }

    //TODO
    //get transaction list for specific product
    //sort by time
    @Override
    public List<Transaction> getProductTransaction(String code) {
        Product product=productDAO.queryByCode(code);
        Integer productid=product.getId();
        return this.transactionDAO.getProductTransaction(productid);
    }


}
