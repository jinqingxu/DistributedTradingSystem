package com.module.dao.daoimpl;

import com.module.bean.Order;
import com.module.dao.OrderDAO;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/20.
 */
public class OrderDAOImpl extends HibernateDaoSupport implements OrderDAO{
    @Override
    public List<Order> getAllOrder() {
        String sql = "from Order";
        return (List<Order>) this.getHibernateTemplate().find(sql);
    }

    public List<Order> getAllOrder(int id){
        String sql = "from Order as order where order.trader.id=?";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()!=0)
            return result;
        else
            return null;
    }

    @Override
    public List<Order> getAllUnfinishedOrder() {
        String status="unfinished";
        String sql = "from Order as order where order.status='"+status+"' and order.left_number>0";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql);
        if(result.size()!=0)
            return result;
        else
            return null;
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
    public void updateLeftNumber(Integer id, Integer leftnumber) {
        String sql = "from Order as order where order.id=?";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()==0){
            System.out.println("try to update leftnumber of a null order");
            return;
        }
        Order or=result.get(0);
        or.setLeft_number(leftnumber);
        this.getHibernateTemplate().update(or);

    }

    @Override
    public void updateStatus(Integer id, String status) {
        String sql = "from Order as order where order.id=?";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()==0){
            System.out.println("try to update status of a null order");
            return;
        }
        Order or=result.get(0);
        or.setStatus(status);
        this.getHibernateTemplate().update(or);
    }
    public void insert(Order or){
        this.getHibernateTemplate().save(or);
    }
    public void delete(Order or){
        this.getHibernateTemplate().delete(or);
    }

    @Override
    public List<Order> getAllUnfinishedOrder(Integer brokerid) {
        String status="unfinished";
        String sql = "from Order as order where order.status='"+status+"' and order.left_number>0 and order.broker.id=+brokerid+";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql);
        if(result.size()!=0)
            return result;
        else
            return null;
    }
    @Override
    public List<Order> getVampOrder() {

        String sql = "from Order as order where order.number>100 and order.left_number>0";
        List<Order> result = (List<Order>) this.getHibernateTemplate().find(sql);
        if(result.size()==0)return null;
        else
            return result;
    }
}
