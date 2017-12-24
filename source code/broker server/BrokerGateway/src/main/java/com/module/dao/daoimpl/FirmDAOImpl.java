package com.module.dao.daoimpl;

import com.module.bean.Firm;
import com.module.bean.Product;
import com.module.dao.FirmDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class FirmDAOImpl extends HibernateDaoSupport implements FirmDAO{
    @Override
    public Firm queryById(Integer id) {
        String sql = "from Firm as firm where firm.id=?";
        List<Firm> result = (List<Firm>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()==0){
            return null;
        }
        else{
            return result.get(0);
        }
    }

    @Override
    public Firm queryByName(String name) {
        String sql = "from Firm as firm where firm.name=?";
        List<Firm> result = (List<Firm>) this.getHibernateTemplate().find(sql,new String[]{name});
        if(result.size()==0){
            return null;
        }
        else{
            return result.get(0);
        }
    }

    public List<Firm> getAllFirms(){
        String sql = "from Firm";
        return (List<Firm>) this.getHibernateTemplate().find(sql);
    }
    public void insert(Firm fr){
        this.getHibernateTemplate().save(fr);
    }
    public void update(Firm fr){
        this.getHibernateTemplate().update(fr);
    }
    public void delete(Firm fr){
        this.getHibernateTemplate().delete(fr);
    }
}
