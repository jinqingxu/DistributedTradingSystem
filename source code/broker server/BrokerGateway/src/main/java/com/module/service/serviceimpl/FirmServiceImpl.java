package com.module.service.serviceimpl;

import com.module.bean.Firm;
import com.module.bean.Product;
import com.module.dao.FirmDAO;
import com.module.service.FirmService;

import java.util.List;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class FirmServiceImpl implements FirmService{
    private FirmDAO firmDAO;

    public FirmDAO getFirmDAO() {
        return firmDAO;
    }

    public void setFirmDAO(FirmDAO firmDAO) {
        this.firmDAO = firmDAO;
    }

    public List<Firm> getAllFirms(){
        return this.firmDAO.getAllFirms();
    }
    public void insert(Firm fr){
        this.firmDAO.insert(fr);
    }
    public void update(Firm fr) {
        this.firmDAO.update(fr);
    }
    public void delete(Firm fr){
        this.firmDAO.delete(fr);
    }

    @Override
    public Firm queryById(Integer id) {
        return firmDAO.queryById(id);
    }

    @Override
    public Firm queryByName(String name) {
        return this.firmDAO.queryByName(name);
    }
}
