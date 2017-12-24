package com.module.restful;

import com.module.bean.*;
import com.module.jms.sender.Sender;
import com.module.jms.sender.SenderImpl;
import com.module.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;


/**
 * Created by jinqingxu on 2017/5/29.
 */
@Component
@Path("/trader")
public class TraderRest {

    @Autowired
    @Qualifier("orderService")
    OrderService orderService;

    @Autowired
    @Qualifier("productService")
    ProductService productService;

    @Autowired
    @Qualifier("brokerService")
    BrokerService brokerService;

    @Autowired
    @QueryParam("cancelOrderService")
    CancelOrderService cancelOrderService;

    @Autowired
    @Qualifier("sender")
    Sender sender;

    @Autowired
    @Qualifier("traderService")
    private TraderService traderService;

    @POST
    @Path("/makeorder")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  String query(@FormParam("number") int num, @FormParam("price") Double price,
                         @FormParam("broker") int brokerid,@FormParam("possession") int p,
                         @FormParam("product") String code, @FormParam("ordertype") String Otype,
                         @FormParam("traderId") int traderid
                         ){
        Order order = new Order();
        String possession;
        if(p==0){
            possession="bid";
        }else {
            possession="ask";
        }
        Product product = productService.queryByCode(code);
        int type;
        if(Otype.equals("Market Order")){
            type =0;
        }else if(Otype.equals("Limit Order")){
            type =1;
        }else {
            type=2;
        }
        Broker broker = brokerService.querybyid(brokerid);
        Trader trader = traderService.querybyid(traderid);
        order.setGiven_price(price);
        order.setNumber(num);
        order.setLeft_number(num);
        order.setProduct(product);
        order.setPossession(possession);
        order.setType(type);
        order.setBroker(broker);
        order.setTrader(trader);
        //order.setBrokerorderid(-1);
        order.setStatus("unfinished");
        order.setTime(new Timestamp(System.currentTimeMillis()));
        orderService.placeOrder(order);
        if(type!=3) {
            sender.sendOrder(order);
        }
        return null;
    }

    @POST
    @Path("/cancelorder")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  String CancelOrder(@FormParam("cancelid") int cancelid
    ){
        Order order = orderService.queryById(cancelid);
        if(order.getLeft_number()>0&&!order.getStatus().equals("canceled")) {
            CancelOrder cancelOrder = new CancelOrder();
            cancelOrder.setCancelorder(order);
            cancelOrder.setTime(new Timestamp(System.currentTimeMillis()));
            cancelOrderService.placeOrder(cancelOrder);
            Sender sender = new SenderImpl();
            sender.sendCancelOrder(cancelOrder);
        }
        return null;
    }


    @GET
    @Path("/getorders")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders() {
        List<Order> orders = orderService.getAllUnfinishedOrder();
        JSONArray jsarray = JSONArray.fromObject(orders);
        JSONArray retarray=new JSONArray();
        if (orders!=null) {
            for (int i = 0; i < orders.size(); i++) {
                //JSONObject tep = jsarray.getJSONObject(i);
                Order tep = orders.get(i);
                JSONObject newjs = new JSONObject();
                newjs.put("id", tep.getId());
                newjs.put("name", tep.getProduct().getProductName());
                newjs.put("code", tep.getProduct().getCode());
                newjs.put("price", tep.getGiven_price());
                newjs.put("amount", tep.getNumber());
                newjs.put("time", tep.getTime().toString());//error
                newjs.put("broker", tep.getBroker().getName());
                newjs.put("possession", tep.getPossession());
                int type = tep.getType();
                if (type == 0) {
                    newjs.put("type", "MarketOrder");
                } else if (type == 1) {
                    newjs.put("type", "LimitOrder");
                } else {
                    newjs.put("type", "StopOrder");
                }
                retarray.add(newjs);
            }
        }
        return retarray.toString();
    }


    @GET
    @Path("/login")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("username") String username,@QueryParam("password") String password) {
        List<Trader> users = traderService.getAllTraders();
        JSONObject obj=new JSONObject();
        for (int i=0;i<users.size();i++){
            String u=users.get(i).getUsername();
            String p=users.get(i).getPassword();
            if (u.equals(username) && p.equals(password)){
                obj.put("result","success");
                obj.put("id",users.get(i).getId());
                return obj.toString();
            }
        }
        obj.put("result","fail");
        return obj.toString();
    }
}
