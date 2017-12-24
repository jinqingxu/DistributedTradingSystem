package com.module.dao;

import com.module.bean.Trader;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public interface TraderDAO {
    public List<Trader> getAllTraders();
    public Trader querybyid(Integer id);
    public void insert(Trader tr);
    public void update(Trader tr);
    public void delete(Trader tr);
}
