package com.module.service.serviceimpl;

import com.module.bean.Firm;
import com.module.bean.Transaction;
import com.module.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jinqingxu on 2017/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations={"classpath:/applicationContext.xml",
})
public class TransactionServiceImplTest {
    @Resource
    private TransactionService transactionService;
    @Test
    public void setFirmDAO() throws Exception {
    }

    @Test
    public void getFirmDAO() throws Exception {
    }

    @Test
    public void setOrderDAO() throws Exception {
    }

    @Test
    public void getOrderDAO() throws Exception {
    }

    @Test
    public void setTransactionDAO() throws Exception {
    }

    @Test
    public void getTransactionDAO() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getAllTransaction() throws Exception {
    }

    @Test
    public void queryById() throws Exception {
    }

    @Test
    public void getAskFirm() throws Exception {
    }

    @Test
    public void getBidFirm() throws Exception {
    }

    @Test
    public void inTrans() throws Exception {
        Firm f=new Firm();
        f.setName("trader");
        Transaction transaction=transactionService.queryById(1);
        transactionService.inTrans(transaction,f);
    }

    @Test
    public void getProductTransaction() throws Exception {
        String productCode="Fe1806";
        List<Transaction> transactionList=transactionService.getProductTransaction(productCode);
    }

}