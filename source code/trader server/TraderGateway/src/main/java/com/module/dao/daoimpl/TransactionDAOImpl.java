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
    public List<Transaction> getAllTransaction(int id) {
        String sql = "from Transaction as transaction where transaction.myorder.trader.id=?";
        List<Transaction> result = (List<Transaction>) this.getHibernateTemplate().find(sql,new Integer[]{id});
        return result;
    }
    @Override
    public double getDealNumberRate(Timestamp start, Timestamp end) {
        String sql="from Transaction as transaction";
        List<Transaction> result = (List<Transaction>) this.getHibernateTemplate().find(sql);
        long sumstart=end.getTime()-21600;
        int sum=0;
        int period=0;
        for(int i=0;i<result.size();++i){
            if(result.get(i).getTime().getTime()>=sumstart&&result.get(i).getTime().getTime()<=end.getTime()){
                sum+=result.get(i).getNumber();
            }
            if(result.get(i).getTime().getTime()>=start.getTime()&&result.get(i).getTime().getTime()<=end.getTime()){
                period+=result.get(i).getNumber();

            }
        }
        return period/sum;
    }
}
