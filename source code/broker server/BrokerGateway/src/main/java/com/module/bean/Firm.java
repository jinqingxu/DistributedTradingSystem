package com.module.bean;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Created by jinqingxu on 2017/5/21.
 */
public class Firm implements Serializable{
    private Integer id;
    private String name;
    private String password;
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getPassword() {
        return password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
