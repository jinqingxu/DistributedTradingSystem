package com.module.restful;

import com.module.bean.Broker;
import com.module.bean.Product;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.jms.sender.Sender;
import com.module.service.OrderService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by siqi.lou on 2017/6/4.
 */
@Component
@Path("/orderbook")
public class OrderBookRest {

    @Autowired
    @Qualifier("redisService")
    RedisService redisService;
    @Autowired
    @Qualifier("orderService")
    OrderService orderService;


    @GET
    @Path("/getorderbook")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public  String getOrderBook(@QueryParam("code") String code,@QueryParam("type") String type){
        OrderBook orderBook=orderService.dbToOrderBook();
        redisService.setOrderBook(orderBook);
        PriorityQueue<DisRecord> l1=orderBook.getDisBuyBook();
        PriorityQueue<DisRecord>l2=orderBook.getDisSellBook();
        List<DisRecord> buy=new ArrayList<DisRecord>();
        List<DisRecord> sell=new ArrayList<DisRecord>();
        buy.addAll(l1);
        sell.addAll(l2);

        if(type.equals("sell")){
            JSONArray jsonArray= JSONArray.fromObject(sell);
            return jsonArray.toString();
        }
        else{
            JSONArray jsonArray= JSONArray.fromObject(buy);
            return jsonArray.toString();

        }

    }


}
