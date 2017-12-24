package websocketbot;

import com.module.entity.DisRecord;
import com.module.entity.OrderBook;

import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import net.sf.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import websocketbot.messages.BuyBookMessage;

import javax.annotation.Resource;

import java.sql.Timestamp;
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
        RedisService redisService=new RedisServiceImpl();
        OrderBook orderBook=redisService.getOrderBook("Cu1706");
        List<DisRecord> buy=new ArrayList<DisRecord>();
        List<DisRecord> sell=new ArrayList<DisRecord>();
        buy.addAll(orderBook.getDisBuyBook());
        buy.remove(0);
        buy.remove(1);
        sell.addAll(orderBook.getDisSellBook());
        JSONArray jsonsell= JSONArray.fromObject(sell);
        JSONArray jsonbuy=JSONArray.fromObject(buy);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        botEndpoint.sendAll(jsonbuy.toString(),jsonsell.toString(),"Cu1706",111.0,d);


    }

}