package com.cx.manager;

import com.cx.model.UserBean;
import com.cx.util.AccountOverDrawnException;
import com.cx.util.InvalidDepositException;

import java.sql.SQLException;

public interface ManagerInterface {

    void  deposit(int add,UserBean userBean) throws InvalidDepositException, SQLException;//存款
    void  withdrawal(UserBean user,int minus) throws AccountOverDrawnException,SQLException;//取款
    boolean register(String uname, String upswd);//注册
    boolean login(String uname, String upswd);//登录
    double  get(UserBean user);//查询
    boolean exist (String uname);//判断是否存在该用户

    //这两个方法 嗯......
    void outDetail (String uname, double money);//转出明细
    void updateTransfer(String name,double afterMoney);//更新转账人的数据库

}
