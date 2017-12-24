package com.module.service.serviceimpl;

import com.module.bean.Broker;
import com.module.service.BrokerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jinqingxu on 2017/5/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations={"classpath:/applicationContext.xml",
})
public class BrokerServiceImplTest {
    @Resource
    private BrokerService brokerService;



    @Test
    public void getBrokerDAO() throws Exception {
    }

    @Test
    public void setBrokerDAO() throws Exception {
    }

    @Test
    public void getAllBrokers() throws Exception {
        List<Broker>broker= this.brokerService.getAllBrokers();
    }

    @Test
    public void querybyid() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }


}
