package com.module.dao;

import com.module.bean.Firm;
import com.module.bean.Product;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public interface FirmDAO {
    public Firm queryById(Integer id);
    public Firm queryByName(String name);
    public List<Firm> getAllFirms();
    public void insert(Firm fr);
    public void update(Firm fr);
    public void delete(Firm fr);
}
