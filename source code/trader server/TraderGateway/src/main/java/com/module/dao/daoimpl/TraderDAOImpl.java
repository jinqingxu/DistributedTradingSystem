package com.module.dao.daoimpl;

import com.module.bean.Order;
import com.module.bean.Trader;
import com.module.dao.TraderDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public class TraderDAOImpl  extends HibernateDaoSupport implements TraderDAO {
   public List<Trader> getAllTraders(){
        String sql = "from Trader";
        return (List<Trader>) this.getHibernateTemplate().find(sql);
    }
   public Trader querybyid(Integer id){
        String sql = "from Trader as trader where trader.id=?";
        List<Trader> result = (List<Trader>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;
    }
   public void insert(Trader tr){
        this.getHibernateTemplate().save(tr);
    }
   public void update(Trader tr){
        this.getHibernateTemplate().update(tr);
    }
   public void delete(Trader tr){
       this.getHibernateTemplate().delete(tr);
   }

}
