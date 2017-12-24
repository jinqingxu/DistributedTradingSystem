package com.module.bean;

import java.io.Serializable;

/**
 * Created by jinqingxu on 2017/5/23.
 */
public class Trader implements Serializable{
    private Integer id;
    private String username;
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
