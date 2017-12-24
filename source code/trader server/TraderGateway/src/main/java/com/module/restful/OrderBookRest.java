package com.module.restful;

import com.module.bean.Broker;
import com.module.bean.Product;
import com.module.entity.DisRecord;
import com.module.entity.OrderBook;
import com.module.jms.sender.Sender;
import com.module.service.BrokerService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Qualifier("brokerService")
    BrokerService brokerService;

    @GET
    @Path("/getorderbook")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public  String getOrderBook(@QueryParam("code") String code,@QueryParam("brokerid") int brokerid,@QueryParam("type") String type){
        /*List<DisRecord> l=new ArrayList<DisRecord>();
        List<DisRecord> l2=new ArrayList<DisRecord>();
        for(int i=10;i<20;++i){
            DisRecord disRecord=new DisRecord(i+0.1,i);
            l.add(disRecord);
        }
        for(int i=20;i<30;++i){
            DisRecord disRecord=new DisRecord(i+0.1,i);
            l2.add(disRecord);

        }
        Broker broker2=this.brokerService.querybyid(1);
        OrderBook orderBook2=new OrderBook();
        orderBook2.setProductCode("Fe1806");
        orderBook2.setBuyBook(l);
        orderBook2.setSellBook(l2);
        orderBook2.setBrokerId(broker2.getId());
        orderBook2.setBrokerName(broker2.getName());
        orderBook2.setProductId(1);
        RedisService redisService=new RedisServiceImpl();
        redisService.setOrderBook(orderBook2);*/

        Broker broker = brokerService.querybyid(brokerid);

        OrderBook orderBook=null;
        if(broker!=null) {
            orderBook = redisService.getOrderBook(code, broker.getName());
        }
        if(orderBook==null)
            return null;

        if(type.equals("sell")){
            /*String tmp=orderBook.sellbookToString();
            Map map=new HashMap();
            map.put("sellbook",orderBook.sellbookToString());
            map.put("productCode",orderBook.getProductCode());
            map.put("brokerid","1");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject.toString();*/
            return orderBook.sellbookToString();
        }
        else{
            /*Map map=new HashMap();
            map.put("buybook",orderBook.buybookToString());
            map.put("productCode",orderBook.getProductCode());
            map.put("brokerid","1");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject.toString();*/
            return  orderBook.buybookToString();
        }
    }

    @GET
    @Path("/price")
    @Produces(MediaType.TEXT_PLAIN)
    public  String getPrice(@QueryParam("code") String code,@QueryParam("brokerid") int id){
        try{
            Broker broker = brokerService.querybyid(id);
            if(broker==null)
                return null;
            Double price = redisService.getPrice(code,broker.getName());
            if(price==0.0)price=45200.00;
            return price.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "0.0";
        }
    }
}
