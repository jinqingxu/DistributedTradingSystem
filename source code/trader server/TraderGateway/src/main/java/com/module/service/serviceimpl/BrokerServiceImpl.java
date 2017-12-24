package com.module.service.serviceimpl;

import com.module.bean.Broker;
import com.module.dao.BrokerDAO;
import com.module.service.BrokerService;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public class BrokerServiceImpl implements BrokerService {
    private BrokerDAO brokerDAO;

    public BrokerDAO getBrokerDAO() {
        return brokerDAO;
    }

    public void setBrokerDAO(BrokerDAO brokerDAO) {
        this.brokerDAO = brokerDAO;
    }

    public List<Broker> getAllBrokers(){
        return this.brokerDAO.getAllBrokers();
    }
    public Broker querybyid(Integer id){
       return this.brokerDAO.querybyid(id);
    }
    public void insert(Broker tr){
          this.brokerDAO.insert(tr);
    }
    public void update(Broker tr){
           this.brokerDAO.update(tr);
    }
    public void delete(Broker tr){
       this.brokerDAO.delete(tr);
    }

    @Override
    public Broker querybyname(String name) {
        return this.brokerDAO.querybyname(name);
    }
}
