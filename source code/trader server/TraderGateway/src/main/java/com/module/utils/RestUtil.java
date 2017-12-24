package com.module.utils;

import net.sf.json.JSONObject;

/**
 * Created by jinqingxu on 2017/5/29.
 */
public class RestUtil {
    public String objectToRestStr(Object o,String jsonpcallback){
        JSONObject json = JSONObject.fromObject(o);
        String result=json.toString();
        return jsonpcallback+'('+result+')';
    }
}
