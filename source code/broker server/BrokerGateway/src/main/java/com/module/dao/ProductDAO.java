package com.module.dao;

import com.module.bean.Product;


import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public interface ProductDAO {
    public List<Product> getAllProducts();
    public Product queryById(Integer id);
    public Product queryByCode(String code);
    public void insert(Product pr);
    public void update(Product pr);
    public void delete(Product pr);

    public List<String> getCategoryList(Integer level,String key);
    public List<Product> queryByCategory2(String key);
}
