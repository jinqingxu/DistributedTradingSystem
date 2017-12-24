package com.module.service.serviceimpl;

import com.module.bean.Broker;
import com.module.bean.Product;
import com.module.dao.ProductDAO;
import com.module.service.ProductService;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/24.
 */
public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO;

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(){
        return this.productDAO.getAllProducts();
    }
    public Product queryById(Integer id){
        return this.productDAO.queryById(id);
    }

    @Override
    public Product queryByCode(String code) {
        return this.productDAO.queryByCode(code);
    }

    public void insert(Product pr){
        this.productDAO.insert(pr);
    }
    public void update(Product pr){
        this.productDAO.update(pr);
    }
    public void delete(Product pr){
        this.productDAO.delete(pr);
    }

    @Override
    public List<String> getCategoryList(Integer level,String key) {
        return this.productDAO.getCategoryList(level,key);
    }

    @Override
    public List<Product> queryByCategory2(String key) {
        return this.productDAO.queryByCategory2(key);
    }

}
