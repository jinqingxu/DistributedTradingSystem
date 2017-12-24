package websocketbot;

import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.service.serviceimpl.BrokerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import websocketbot.messages.BuyBookMessage;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jinqingxu on 2017/6/4.
 */
public class BotEndpointTest {
    @Resource
    private BotEndpoint botEndpoint;



    @Before
    public void setup(){
        botEndpoint=new BotEndpoint();
    }

    @Test
    public void openConnection() throws Exception {
    }

    @Test
    public void error() throws Exception {
    }

    @Test
    public void sendAll() throws Exception {
        BotEndpoint botEndpoint=new BotEndpoint();
        List<DisRecord> l=new ArrayList<DisRecord>();
        List<DisRecord> l2=new ArrayList<DisRecord>();
        for(int i=10;i<20;++i){
            DisRecord disRecord=new DisRecord(i+0.1,i);
            l.add(disRecord);
        }
        for(int i=20;i<30;++i){
            DisRecord disRecord=new DisRecord(i+0.1,i);
            l2.add(disRecord);

        }
        OrderBook orderBook=new OrderBook();
        orderBook.setProductCode("Fe1806");
        orderBook.setBuyBook(l);
        orderBook.setBrokerId(1);
        orderBook.setSellBook(l2);
        botEndpoint.sendAll(orderBook,14.6);

    }

}