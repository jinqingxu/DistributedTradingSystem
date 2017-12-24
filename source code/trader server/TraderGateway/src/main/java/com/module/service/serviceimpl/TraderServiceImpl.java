package com.module.service.serviceimpl;

import com.module.bean.Trader;
import com.module.dao.TraderDAO;
import com.module.service.TraderService;
import com.module.service.TransactionService;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public class TraderServiceImpl implements TraderService {
    private TraderDAO traderDAO;

    public TraderDAO getTraderDAO() {
        return traderDAO;
    }

    public void setTraderDAO(TraderDAO traderDAO) {
        this.traderDAO = traderDAO;
    }

    public List<Trader> getAllTraders(){
        return this.traderDAO.getAllTraders();
    }
    public Trader querybyid(Integer id){
        return this.traderDAO.querybyid(id);
    }
    public void insert(Trader tr){
        this.traderDAO.insert(tr);
    }
    public void update(Trader tr){
        this.traderDAO.update(tr);
    }
    public void delete(Trader tr){
        this.traderDAO.delete(tr);
    }
}
