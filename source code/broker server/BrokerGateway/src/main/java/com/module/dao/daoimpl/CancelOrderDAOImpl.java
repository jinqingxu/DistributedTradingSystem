package com.module.dao.daoimpl;

import com.module.bean.CancelOrder;
import com.module.dao.CancelOrderDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Created by jinqingxu on 2017/6/2.
 */
public class CancelOrderDAOImpl extends HibernateDaoSupport implements CancelOrderDAO {
    public void insert(CancelOrder cancelOrder){
        this.getHibernateTemplate().save(cancelOrder);
    }

}
