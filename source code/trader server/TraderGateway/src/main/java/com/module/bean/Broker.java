package com.module.bean;

import java.io.Serializable;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class Broker implements Serializable{
    private Integer id;
    private String name;
    private String ip;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}