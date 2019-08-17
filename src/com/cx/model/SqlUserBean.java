package com.cx.model;

import com.cx.util.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 实现对数据库t_user表中参数做封装
 */


public class SqlUserBean {
    private String user_name;
    private  String user_password;
    private  int user_flag;// 定义用户的类型
    private  double banlance;
    private  int user_id;
    private int lockJudeg; // 是否被冻结






    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_flag(int user_flag) {
        this.user_flag = user_flag;
    }

    public void setBanlance(double banlance) {
        this.banlance = banlance;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public void setLockJudeg(UserBean user,int lockJudge) {

        String uname = user.getUsername();

        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;

        try {
            // String sql = "SELECT * FROM t_user WHERE user_name='" + uname + "'and banlance '"+ (money)+"'";
            String sql = "update t_user set lockJudge= '" + lockJudge + "' where user_name='" + uname + "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);

           // lockDetail(user);


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }

    }


    public int getLockJudeg(UserBean user) {

        String uname = user.getUsername();
        System.out.println("jjjjj");
        System.out.println(uname);
        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        //double money = 0;
        int lockJudge =-1;
        try {
            String sql = "SELECT * FROM t_user";
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(rs.next());
            boolean t = rs.next();
            while (rs.next()) {
                if (uname.equals(rs.getString("user_name"))) {
                    System.out.println("我在拿lock");
                    lockJudge= Integer.parseInt(rs.getString("lockJudge"));
                    break;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("查询flag");
        System.out.println(lockJudge);
        //moneyBean.setMoney(money);
        //moneyBean.getMoney();
        return  lockJudge;
    }


    public int getUser_id(UserBean user) {
        String uname = user.getUsername();
        System.out.println("jjjjj");
        System.out.println(uname);
        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        //double money = 0;
        int user_id =-1;

        try {
            String sql = "SELECT * FROM t_user ";
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(rs.next());
            boolean t = rs.next();
            while (rs.next()) {
                if (uname.equals(rs.getString("user_name"))) {
                    System.out.println("cbsjabcka");
                    System.out.println("我在拿id");
                    user_id= Integer.parseInt(rs.getString("user_id"));
                    // money = Double.parseDouble(rs.getString("banlance"));
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("查询user_id");
        System.out.println(user_id);
        //moneyBean.setMoney(money);
        //moneyBean.getMoney();
        return  user_id;
    }

    public String getUser_name(int user_id) {

            Connection conn = null;
            try {
                conn = JDBCUtils.dbOpen();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet rs = null;
            Statement stmt = null;
            //double money = 0;
            String user_name= null;
            try {
                String sql = "SELECT * FROM t_user";
                stmt = (Statement) conn.createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println(rs.next());
                boolean t = rs.next();
                while (rs.next()) {
                    if (user_id==(rs.getInt("user_id"))) {
                        System.out.println("cbsjabcka");
                        System.out.println("我在拿名字");
                       user_name= (rs.getString("user_name"));
                        break;
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                JDBCUtils.dbClose(conn, stmt, rs);
            }
            System.out.println("查询flag");
            System.out.println(user_name);
            //moneyBean.setMoney(money);
            //moneyBean.getMoney();
            return  user_name;
        }




    public String getUser_password() {
        return user_password;
    }


    public int getUser_flag(UserBean user) {

        String uname = user.getUsername();
        System.out.println("jjjjj");
        System.out.println(uname);
        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        //double money = 0;
        int flag =-1;
        try {
            String sql = "SELECT * FROM t_user";
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(rs.next());
            boolean t = rs.next();
            while (rs.next()) {
                if (uname.equals(rs.getString("user_name"))) {
                    System.out.println("cbsjabcka");
                    System.out.println("我在拿flag");
                    flag= Integer.parseInt(rs.getString("user_flag"));
                    break;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("查询flag");
        System.out.println();
        //moneyBean.setMoney(money);
        //moneyBean.getMoney();
        return  flag;
    }


    public double getBanlance(UserBean user) {
            MoneyBean moneyBean = MoneyBean.getInstance();
        String uname = user.getUsername();
        System.out.println("jjjjj");
        System.out.println(uname);
        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        double money = 0;

        try {
            String sql = "SELECT * FROM t_user ";
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(rs.next());
            boolean t = rs.next();
            while (rs.next()) {
                if (uname.equals(rs.getString("user_name"))) {
                    System.out.println("cbsjabcka");
                    System.out.println("oookkk");
                    money = Double.parseDouble(rs.getString("banlance"));
                    break;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("查询余额");
        System.out.println(money);
        moneyBean.setMoney(money);
        moneyBean.getMoney();
        return money;
    }

}
