package com.module.dao.daoimpl;

import com.module.bean.Broker;
import com.module.bean.Product;
import com.module.bean.Trader;
import com.module.dao.ProductDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public class ProductDAOImpl  extends HibernateDaoSupport implements ProductDAO {
    public List<Product> getAllProducts(){
        String sql = "from Product";
        return (List<Product>) this.getHibernateTemplate().find(sql);
    }
    public Product querybyid(Integer id){
        String sql = "from Product as product where product.id=?";
        List<Product> result = (List<Product>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;
    }
    public void insert(Product pr){
        this.getHibernateTemplate().save(pr);
    }
    public void update(Product pr){
        this.getHibernateTemplate().update(pr);
    }
    public void delete(Product pr){
        this.getHibernateTemplate().delete(pr);
    }

    @Override
    public List<String> getCategoryList(Integer level,String key) {
        String queryString="";
        List list=null;
        switch(level){
            case 0:
                queryString = "select distinct category1 from Product";
                list=getHibernateTemplate().find(queryString);
                break;
            case 1:
                queryString = "select distinct category2 from Product as product where product.category1=?";
                list=getHibernateTemplate().find(queryString,new String[]{key});
                break;
        }
        return list;
    }

    @Override
    public List<Product> queryByCategory2(String key) {
        String sql = "from Product as product where product.category2=?";
        List<Product> result = (List<Product>) this.getHibernateTemplate().find(sql,new String[]{key});
        if(result.size()!=0)
            return result;
        else
            return null;
    }

    @Override
    public Product querybyCode(String code) {
        String sql = "from Product as product where product.code=?";
        List<Product> result = (List<Product>) this.getHibernateTemplate().find(sql,new String[]{code});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;
    }
}
