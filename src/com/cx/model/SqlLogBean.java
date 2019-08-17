package com.cx.model;

/**
 * 对数据库的表t_log，的参数做封装
 * 实现set和get
 */
public class SqlLogBean {


        private int log_id,userid;
        private String log_type,log_amount,user_name;

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public void setLog_amount(String log_amount) {
        this.log_amount = log_amount;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getLog_id() {
        return log_id;
    }

    public int getUserid() {
        return userid;
    }

    public String getLog_amount() {
        return log_amount;
    }

    public String getLog_type() {
        return log_type;
    }

    public String getUser_name() {
        return user_name;
    }
}
