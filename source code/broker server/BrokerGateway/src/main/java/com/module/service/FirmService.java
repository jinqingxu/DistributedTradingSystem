package com.module.service;

import com.module.bean.Firm;
import com.module.bean.Order;
import com.module.bean.Product;
import com.module.dao.OrderDAO;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public interface FirmService {
    public List<Firm> getAllFirms();
    public Firm queryById(Integer id);
    public Firm queryByName(String name);
    public void insert(Firm pr);
    public void update(Firm pr);
    public void delete(Firm pr);


}
