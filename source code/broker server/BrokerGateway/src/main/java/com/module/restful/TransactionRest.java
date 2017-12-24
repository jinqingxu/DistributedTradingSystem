package com.module.restful;

import com.module.bean.Firm;
import com.module.bean.Transaction;
import com.module.service.FirmService;
import com.module.service.TransactionService;
//import com.sun.deploy.nativesandbox.comm.Response;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinqingxu on 2017/5/29.
 */
@Component
@Path("/transaction")
public class TransactionRest {


    @Autowired
    @Qualifier("transactionService")
    private TransactionService transactionService;

    @Autowired
    @Qualifier("firmService")
    private FirmService firmService;

    @GET
    @Path("/gettransactions")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query(@QueryParam("product") String code, @QueryParam("firmName") String firm) {

        List<Transaction> list = transactionService.getProductTransaction(code);
        Firm f = firmService.queryById(1);
        List<Tr> result = new ArrayList<Tr>();
        JSONArray ret=new JSONArray();
        for(Transaction t:list){
            Double price = t.getPrice();
            int num = t.getNumber();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
            String time = df.format(t.getTime());
            if(transactionService.inTrans(t,f)){
                String ask = transactionService.getAskFirm(t).getName();
                String bid = transactionService.getBidFirm(t).getName();
                Tr tr = new Tr(code,price,num,ask,bid,time);
                JSONObject tep=new JSONObject();
                tep.put("code",code);
                tep.put("price",price);
                tep.put("num",num);
                tep.put("ask",ask);
                tep.put("bid",bid);
                tep.put("time",time);
                ret.add(tep);
                result.add(tr);
            }else{
                Tr tr = new Tr(code,price,num,time);
                JSONObject tep=new JSONObject();
                tep.put("code",code);
                tep.put("price",price);
                tep.put("num",num);
                tep.put("time",time);
                ret.add(tep);
                result.add(tr);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(result);

        return ret.toString();
    }

    private class Tr implements Serializable{
        String code;
        Double price;
        int num;
        String ask;
        String bid;
        String time;

        Tr(String code,Double price,int num,String ask,String bid,String time){
            this.code=code;
            this.price=price;
            this.num=num;
            this.ask=ask;
            this.bid=bid;
            this.time=time;
        }

        Tr(String code,Double price,int num, String time){
            this.code=code;
            this.price=price;
            this.num=num;
            this.ask="";
            this.bid="";
            this.time=time;
        }

        @Override
        public String toString() {
            return "tr{" +
                    "code='" + code + '\'' +
                    ", price=" + price +
                    ", num=" + num +
                    ", ask='" + ask + '\'' +
                    ", bid='" + bid + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @GET
    @Path("/{product}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransaction(@PathParam("product") String code){
        List<Transaction> list = transactionService.getProductTransaction(code);
        JSONArray jsonArray = new JSONArray();
        for(Transaction t:list){
            Double price = t.getPrice();
            int num = t.getNumber();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
            String time = df.format(t.getTime());
            String ask = transactionService.getAskFirm(t).getName();
            String bid = transactionService.getBidFirm(t).getName();
            Tr tr = new Tr(code,price,num,ask,bid,time);
            JSONObject tep=new JSONObject();
            tep.put("code",code);
            tep.put("price",price);
            tep.put("num",num);
            tep.put("ask",ask);
            tep.put("bid",bid);
            tep.put("time",time);
            jsonArray.add(tep);
        }

        return jsonArray.toString();
    }

}
