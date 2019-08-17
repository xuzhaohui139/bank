package com.cx.model;

/**
 * 实现对user的取得和存入的封装
 */
public class UserBean {
     private String username;
     private  String userpassword;


    public String getUsername() {
        return this.username;
    }

    public String getUserpassword() {
        return this.userpassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }



}
