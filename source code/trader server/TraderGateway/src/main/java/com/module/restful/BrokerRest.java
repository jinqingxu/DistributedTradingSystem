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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 2017/6/4.
 */
@Component
@Path("/broker")
public class BrokerRest {
    @Autowired
    @Qualifier("brokerService")
    BrokerService brokerService;

    @GET
    @Path("/getbrokers")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query() {
        List<Broker> brokers = brokerService.getAllBrokers();
        List<String> names=new ArrayList<String>();
        List<Integer> ids=new ArrayList<Integer>();
        for (int i=0;i<brokers.size();i++){
            names.add(brokers.get(i).getName());
            ids.add(brokers.get(i).getId());
        }
        JSONObject obj=new JSONObject();
        obj.put("names",names);
        obj.put("ids",ids);
        return obj.toString();
    }

    @GET
    @Path("/getBrokerIp/{brokerId}")
    @Produces(MediaType.TEXT_PLAIN)
    //返回一个BrokerIp
    public String getBroker(@PathParam("brokerId") int id) {
        try {
            Broker broker = brokerService.querybyid(id);
            return broker.getIp();
        } catch (Exception e) {
            e.printStackTrace();
            return "No Such Broker";
        }
    }


    @Path("/getbrokerip")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query2(@QueryParam("brokerid") Integer brokerid) {
        List<Broker> brokers = brokerService.getAllBrokers();
        JSONObject obj=new JSONObject();
        for (int i=0;i<brokers.size();i++){
            Integer tep=brokers.get(i).getId();
            if (tep.equals(brokerid)){
                obj.put("ip",brokers.get(i).getIp());
            }
        }
        return obj.toString();
    }
}
