package com.module.dao.daoimpl;

import com.module.bean.Order;
import com.module.bean.PriceRecord;
import com.module.dao.PriceRecordDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by siqi.lou on 2017/6/5.
 */
public class PriceRecordDaoImpl extends HibernateDaoSupport implements PriceRecordDao{
    @Override
    public void insert(PriceRecord or) {
        this.getHibernateTemplate().save(or);
    }

    @Override
    public List<PriceRecord> getAllPriceRecord(java.lang.String code,String broker) {
        String sql = "from PriceRecord as p " +
                "where p.code = ? and p.brokerName = ?"+
                "order by p.time desc ";
        List<PriceRecord> list = getHibernateTemplate().find(sql,new String[]{code,broker});
        return list;
    }


}
