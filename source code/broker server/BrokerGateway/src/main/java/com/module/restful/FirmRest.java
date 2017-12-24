package com.module.restful;
import com.module.bean.Firm;
import com.module.bean.Order;
import com.module.service.FirmService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/6/5.
 */
@Component
@Path("/firm")
public class FirmRest {
    @Autowired
    @Qualifier("firmService")
    FirmService firmService;

    @GET
    @Path("/getfirms")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders() {
        List<Firm> firms=firmService.getAllFirms();
        List<String> names=new ArrayList<String>();
        List<String> ips=new ArrayList<String>();
        for (int i=0;i<firms.size();i++){
            names.add(firms.get(i).getName());
            ips.add(firms.get(i).getIp());
        }
        JSONObject retobj=new JSONObject();
        retobj.put("names",names);
        retobj.put("ips",ips);
        return retobj.toString();
    }
}
