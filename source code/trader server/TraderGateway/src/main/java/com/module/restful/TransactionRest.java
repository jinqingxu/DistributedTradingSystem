package com.module.restful;

import com.module.bean.Broker;
import com.module.bean.Order;
import com.module.bean.Trader;
import com.module.bean.Transaction;
import com.module.entity.OrderBook;
import com.module.service.BrokerService;
import com.module.service.TraderService;
import com.module.service.TransactionService;
import com.module.util.RedisService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by siqi.lou on 2017/6/5.
 */
@Component
@Path("/transaction")
public class TransactionRest {

    @Autowired
    @Qualifier("traderService")
    TraderService traderService;

    @Autowired
    @Qualifier("transactionService")
    TransactionService transactionService;

    @GET
    @Path("/{traderId}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public  String getOrderBook(@PathParam("traderId") int id){
        try {
            Trader trader = traderService.querybyid(id);
            if (trader == null)
                return null;
            List<Transaction> list = transactionService.getAllTransaction(id);
            JSONArray retarray = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                //JSONObject tep = jsarray.getJSONObject(i);
                Transaction tep = list.get(i);
                Order or = tep.getMyorder();
                JSONObject newjs = new JSONObject();
                newjs.put("id", or.getId());
                newjs.put("code", or.getProduct().getCode());
                newjs.put("price", tep.getPrice());
                newjs.put("amount", tep.getNumber());
                newjs.put("time", tep.getTime().toString());
                newjs.put("possession",or.getPossession());
                newjs.put("Firm", tep.getFirmname());
                retarray.add(newjs);
            }
            return retarray.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
