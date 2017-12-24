package com.module.dao;

import com.module.bean.Product;
import com.module.bean.Trader;
import com.module.thread.ProcessStopOrderThread;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public interface ProductDAO {
    public List<Product> getAllProducts();
    public Product querybyid(Integer id);
    public void insert(Product pr);
    public void update(Product pr);
    public void delete(Product pr);
    public Product querybyCode(String code);
    public List<String> getCategoryList(Integer level,String key);
    public List<Product> queryByCategory2(String key);

}
