package com.cx.dao;

import com.cx.model.SqlLogBean;
import com.cx.model.SqlUserBean;
import com.cx.model.UserBean;
import com.cx.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 此类用于遍历数据库中的明细表，将结果存放到集合之中，更好的加以显示
 * 也算是对数据的一种操作，
 * 就放在了Dao层
 */

public class UserDao {

    private Connection conn=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
   SqlUserBean sqlUserBean =new SqlUserBean();// log表中参数的封装


    /**
     * 查询所有用户的明细
     * @return List
     */

    //查询所有用户的明细
    public List<SqlLogBean> queryAllUser(){//SqlLogBean 创建 类型的集合，并返回集合类型
        String sql="select * from t_log";//
        List<SqlLogBean> list=new ArrayList<SqlLogBean>();
        try {
            conn= JDBCUtils.dbOpen();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            System.out.println(ps.toString());
            while(rs.next()){//遍历表，将对应信息 中设置到list集合中，
               SqlLogBean logBean =new SqlLogBean();
               logBean.setLog_id(rs.getInt(1));
               logBean.setLog_type(rs.getString(2));
               logBean.setLog_amount(rs.getString(3));
               logBean.setUser_name(sqlUserBean.getUser_name(rs.getInt(4)));//通过id 找到相应的名字
               list.add(logBean);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 查询某个用户的明细
     * @return List
     */
    //查询某个用户的明细
    public List<SqlLogBean> queryOneUser(UserBean userBean){
        String name =userBean.getUsername();
        System.out.println(name+" 是你在查询吧");
        String sql="select * from t_log";
        List<SqlLogBean> list=new ArrayList<SqlLogBean>();
        try {
            conn= JDBCUtils.dbOpen();;
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            System.out.println(ps.toString());
            while(rs.next()){
                SqlLogBean logBean =new SqlLogBean();
                if(rs.getInt(4)==sqlUserBean.getUser_id(userBean)) {
                    logBean.setLog_id(rs.getInt(1));
                    logBean.setLog_type(rs.getString(2));
                    logBean.setLog_amount(rs.getString(3));
                    //logBean.setUserid(rs.getInt(4));
                    logBean.setUser_name(sqlUserBean.getUser_name(rs.getInt(4)));
                    list.add(logBean);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
