package DaoTest;

import com.module.bean.Firm;
import com.module.bean.Order;
import com.module.bean.Product;
import com.module.dao.OrderDAO;
import com.module.dao.daoimpl.OrderDAOImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by jinqingxu on 2017/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations={"classpath:/applicationContext.xml",
})

public class OrderDAOImplTest {

    @Resource
    OrderDAO orderDAO;



    @Test
    public void getAllOrder() throws Exception {
    }

    @Test
    public void queryById() throws Exception {
    }

    @Test
    public void updateByOrder() throws Exception {
    }

    @Test
    public void updateLeftNumber() throws Exception {
    }

    @Test
    public void updateStatus() throws Exception {
    }

    @Test
    public void insert() throws Exception {
        Order or1=new Order();
        Order or2=this.orderDAO.queryById(90);
        or2.setLeft_number(1);
        this.orderDAO.updateLeftNumber(or2.getId(),or2.getLeft_number());
        or2.setStatus("canceled");
        this.orderDAO.updateStatus(or2.getId(),or2.getStatus());
        Order or=new Order();
        or.setStatus("finished");
        or.setLeft_number(10);
        or.setGiven_price(13.6);
        Firm firm=new Firm();
        firm.setName("jiaoda");
        firm.setIp("127.0.0.1");
        firm.setId(1);
        or.setFirm(firm);
        or.setNumber(10);
        Product p=new Product();
        p.setCode("123456");
        p.setId(1);
        or.setProductCode(p);
        Date date = new Date();
        Timestamp no = new Timestamp(date.getTime());
        or.setTime(no);
        or.setType(1);
        or.setType_possession("bid");
        orderDAO.insert(or);
    }

    @Test
    public void delete() throws Exception {
    }

}