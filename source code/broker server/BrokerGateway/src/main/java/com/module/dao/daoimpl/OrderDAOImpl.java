package com.module.dao.daoimpl;

import com.module.bean.Order;
import com.module.dao.OrderDAO;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class OrderDAOImpl extends HibernateDaoSupport implements OrderDAO{
    private SessionFactory sessionFactory;
    @Override
    public List<Order> getAllOrder() {
        String sql = "from Order";
        return (List<Order>) this.getHibernateTemplate().find(sql);
    }


    public Order queryById(Integer id){
        String sql = "from Order as order where order.id=?";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;

    }
    public void update(Order or){

        this.getHibernateTemplate().update(or);
    }

    @Override
    public int updateLeftNumber(Integer id, Integer leftnumber) {
        System.out.println("leftnumber");
        String sql = "from Order as order where order.id=?";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()==0)return -1;
        else{
            Order or=result.get(0);
            int version=or.getVersion();
            or.setLeft_number(leftnumber);
            or.setVersion(version+1);
            //List<Order> result2 = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
            //if(result2.get(0).getVersion()!=version)return -1;
            this.getHibernateTemplate().update(or);
            return 0;

        }




    }

    @Override
    public int updateStatus(Integer id, String status) {
        String sql = "from Order as order where order.id=?";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()==0)return -1;
        else{
            Order or=result.get(0);
            int version=or.getVersion();
            or.setStatus(status);
            or.setVersion(version+1);
            //List<Order>result2 = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
            //if(result2.get(0).getVersion()!=version)return -1;
            this.getHibernateTemplate().update(or);
            return 0;

        }
    }
    public void insert(Order or){

        this.getHibernateTemplate().save(or);
    }
    public void delete(Order or){
        this.getHibernateTemplate().delete(or);
    }


}
