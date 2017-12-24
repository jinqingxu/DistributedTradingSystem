package com.module.service;

import com.module.bean.Broker;
import com.module.bean.Product;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public interface ProductService {
    public List<Product> getAllProducts();
    public Product querybyid(Integer id);
    public void insert(Product pr);
    public void update(Product pr);
    public void delete(Product pr);
    public Product queryByCode(String code);
    //started from 0
    public List<String> getCategoryList(Integer level,String key);
    public List<Product> queryByCategory2(String key);

}
