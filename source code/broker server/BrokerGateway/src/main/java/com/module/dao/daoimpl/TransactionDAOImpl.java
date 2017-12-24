package com.module.dao.daoimpl;

import com.module.bean.Transaction;
import com.module.dao.TransactionDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/22.
 */
public class TransactionDAOImpl  extends HibernateDaoSupport implements TransactionDAO {


    @Override
    public void delete(Transaction tr) {
        this.getHibernateTemplate().delete(tr);
    }

    @Override
    public void insert(Transaction tr) {

        this.getHibernateTemplate().save(tr);
    }

    @Override
    public void update(Transaction tr) {
        this.getHibernateTemplate().update(tr);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        String sql = "from Transaction";
        return (List<Transaction>) this.getHibernateTemplate().find(sql);
    }

    @Override
    public Transaction queryById(Integer id) {
        String sql = "from Transaction as transaction where transaction.id=?";
        List<Transaction> result = (List<Transaction>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        if(result.size()!=0)
            return result.get(0);
        else
            return null;
    }

    @Override
    public List<Transaction> getProductTransaction(Integer productid) {
        String sql = "from Transaction as transaction";
        List<Transaction> result = (List<Transaction>) this.getHibernateTemplate().find(sql);
        if(result.size()!=0){
            for(int i=0;i<result.size();++i){
                if(result.get(i).getAskorder().getProductCode().getId()==productid){
                    continue;
                }
                else{
                    result.remove(i);
                }
            }
            return result;
        }

        else
            return null;
    }


}
