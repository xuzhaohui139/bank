package com.cx.dao;

import com.cx.model.UserBean;
import com.cx.util.AccountOverDrawnException;

import java.sql.SQLException;

public interface BankDaoInterface {

    boolean register(String name, String pswd)throws Exception;//注册
    boolean login(String name, String pswd) throws  Exception;//登录
    void  deposit(int add, UserBean user) throws Exception;//存款
    boolean withdrawal(UserBean user,int minus) throws AccountOverDrawnException,SQLException;//取款
    boolean tranfer(String uname, double money) throws Exception;//转账

    // 这两个方法可以写在模型层的哦，
    boolean finduser(String name) throws SQLException;// 判断用户是否存在
    double getBalance(UserBean user) throws Exception;//获取金额


}
