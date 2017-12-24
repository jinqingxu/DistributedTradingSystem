package com.module.dao.daoimpl;

import com.module.bean.Broker;
import com.module.bean.Trader;
import com.module.dao.BrokerDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public class BrokerDAOImpl  extends HibernateDaoSupport implements BrokerDAO {
    public List<Broker> getAllBrokers(){
        String sql = "from Broker";
        return (List<Broker>) this.getHibernateTemplate().find(sql);
    }
    public Broker querybyid(Integer id){
        String sql = "from Broker as broker where broker.id=?";
        List<Broker> result = (List<Broker>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;
    }
    public void insert(Broker br){
        this.getHibernateTemplate().save(br);
    }
    public void update(Broker br){
        this.getHibernateTemplate().update(br);

    }
    public void delete(Broker br){
        this.getHibernateTemplate().delete(br);
    }

    @Override
    public Broker querybyname(String name) {
        String sql = "from Broker as broker where broker.name=?";
        List<Broker> result = (List<Broker>) this.getHibernateTemplate().find(sql,new String[]{name});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;
    }
}
