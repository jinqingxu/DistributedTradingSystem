package com.module.restful;

import com.module.bean.Order;
import com.module.service.OrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by siqi.lou on 2017/6/5.
 */

@Component
@Path("/order")
public class OrderRest {
    @Autowired
    @Qualifier("orderService")
    OrderService orderService;

    @GET
    @Path("/getOrders")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders() {
        List<Order> orders = orderService.getAllOrders();
        JSONArray retarray=new JSONArray();
        for(int i=0;i<orders.size();i++) {
            Order tep=orders.get(i);
            JSONObject newjs=new JSONObject();
            newjs.put("id",tep.getId());
            newjs.put("code",tep.getProductCode().getCode());
            newjs.put("price",tep.getGiven_price());
            newjs.put("amount",tep.getNumber());
            newjs.put("time",tep.getTime().toString());
            newjs.put("firm",tep.getFirm().getName());
            newjs.put("possession",tep.getType_possession());
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
            retarray.add(newjs);
        }
        return retarray.toString();
    }

    @GET
    @Path("/getUnsettledOrders")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUnsettledOrders() {
        List<Order> orders = orderService.getUnfinishedOrders();
        JSONArray jsarray = JSONArray.fromObject(orders);
        JSONArray retarray=new JSONArray();
        for(int i=0;i<orders.size();i++) {
            //JSONObject tep = jsarray.getJSONObject(i);
            Order tep=orders.get(i);
            JSONObject newjs=new JSONObject();
            newjs.put("id",tep.getId());
            newjs.put("code",tep.getProductCode().getCode());
            newjs.put("price",tep.getGiven_price());
            newjs.put("amount",tep.getNumber());
            newjs.put("time",tep.getTime().toString());//error
            newjs.put("firm",tep.getFirm().getName());
            newjs.put("possession",tep.getType_possession());
            newjs.put("status","traded");
            newjs.put("left_number",tep.getLeft_number());
            int type=tep.getType();
            if (type==0){
                newjs.put("type","MarketOrder");
            }
            else if (type==1){
                newjs.put("type","LimitOrder");
            }
            retarray.add(newjs);
        }
        return retarray.toString();
    }
}
