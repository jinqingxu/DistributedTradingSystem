package com.module.dao.daoimpl;

import com.module.bean.PriceRecord;
import com.module.dao.PriceRecordDao;
import com.module.service.serviceimpl.PriceRecordServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by siqi.lou on 2017/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class PriceRecordDaoImplTest {

    @Autowired
    @Qualifier("priceRecordDao")
    PriceRecordDao priceRecordDao;

    @Resource
    PriceRecordServiceImpl priceRecordService;

    @Test
    public void insert() throws Exception {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        PriceRecord priceRecord = new PriceRecord(192.3,"xxx","x",t);
        priceRecordDao.insert(priceRecord);
    }

    @Test
    public void getNewPriceRecord() throws Exception {
        PriceRecord p = priceRecordService.getNewPriceRecord("xxx","x");
    }

    @Test
    public void getAllPriceRecord() throws Exception {
        priceRecordDao.getAllPriceRecord("xxx","x");
    }

}