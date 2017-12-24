package com.module.restful;

import com.module.bean.Product;
import com.module.service.ProductService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/6/3.
 */
@Component
@Path("/product")
public class ProductRest {

    @Autowired
    @Qualifier("productService")
    ProductService productService;

    @GET
    @Path("/getcategory1")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query(){
        List<String> types=productService.getCategoryList(0,"");
        JSONObject retjs=new JSONObject();
        retjs.put("types",types);
        return retjs.toString();
    }

    @GET
    @Path("/getcategory2")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query2(@QueryParam("typename") String typename){
        List<String> types=productService.getCategoryList(1,typename);
        JSONObject retjs=new JSONObject();
        retjs.put("types",types);
        return retjs.toString();
    }

    //return swap name, code and the swaps' order book
    @GET
    @Path("/getswap")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query3(@QueryParam("productname") String productname){
        List<Product> tep=productService.queryByCategory2(productname);
        List<String> ret=new ArrayList<String>();
        for (int i=0;i<tep.size();i++){
            ret.add(tep.get(i).getProductName());
        }
        JSONObject retjs=new JSONObject();
        retjs.put("types",ret);
        return retjs.toString();
    }

    @GET
    @Path("/getproducts")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String query4(){
        List<String> types=productService.getCategoryList(0,"");
        List<List<String>> products=new ArrayList<List<String>>();
        List<List<List<String>>> swaps=new ArrayList<List<List<String>>>();
        for (int i=0;i<types.size();i++){
            List<String> tep=productService.getCategoryList(1,types.get(i));
            products.add(tep);
            List<List<String>> tep2=new ArrayList<List<String>>();
            for (int j=0;j<tep.size();j++){
                List<Product> prods=productService.queryByCategory2(tep.get(j));
                List<String> names=new ArrayList<String>();
                for (int k=0;k<prods.size();k++){
                    names.add(prods.get(k).getProductName());
                }
                tep2.add(names);
            }
            swaps.add(tep2);
        }
        JSONObject retjs=new JSONObject();
        retjs.put("types",types);
        retjs.put("products",products);
        retjs.put("swaps",swaps);
        return retjs.toString();
    }
}
