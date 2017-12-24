package com.module.restful;

import com.module.bean.Order;
import com.module.service.OrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by siqi.lou on 2017/6/4.
 */
@Component
@Path("/order")
public class OrderRest {

    @Autowired
    @Qualifier("orderService")
    OrderService orderService;

    @GET
    @Path("/getallorder/{traderId}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("traderId") int id) {
        //response.setHeader("Access-Control-Allow-Origin", "*");
        List<Order> orders = orderService.getAllOrder(id);
        //JSONArray jsarray = JSONArray.fromObject(orders);
        JSONArray retarray=new JSONArray();
        if(orders==null){
            return null;
        }
        for(int i=0;i<orders.size();i++) {
            //JSONObject tep = jsarray.getJSONObject(i);
            Order tep=orders.get(i);
            JSONObject newjs=new JSONObject();
            newjs.put("id",tep.getId());
            newjs.put("name",tep.getProduct().getProductName());
            newjs.put("code",tep.getProduct().getCode());
            newjs.put("price",tep.getGiven_price());
            newjs.put("amount",tep.getNumber());
            newjs.put("time",tep.getTime().toString());
            newjs.put("broker",tep.getBroker().getName());
            newjs.put("possession",tep.getPossession());
            String status="unsettled";
            if(tep.getStatus().equals("canceled")){
                status = "canceled";
            }else if(tep.getLeft_number()==0){
                status = "traded";
            }
            newjs.put("status",status);
            newjs.put("left_number",tep.getLeft_number());
            int type=tep.getType();
            if (type==0){
                newjs.put("type","MarketOrder");
            }
            else if (type==1){
                newjs.put("type","LimitOrder");
            }
            else{
                newjs.put("type","StopOrder");
            }
            retarray.add(newjs);
        }
        return retarray.toString();
    }
}
