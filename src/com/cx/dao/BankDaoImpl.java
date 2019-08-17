package com.cx.dao;

import com.cx.model.MoneyBean;
import com.cx.model.SqlUserBean;
import com.cx.model.UserBean;
import com.cx.util.AccountOverDrawnException;
import com.cx.util.JDBCUtils;

import java.sql.*;


/**
 * BankDaoImpl 主要对于功能实现，操作封装，实现了BankDaoInterface接口
 *
 */
public class BankDaoImpl implements BankDaoInterface {

    MoneyBean moneyBean = MoneyBean.getInstance();

    /**
     * 注册功能，
     * 传入两个参数 name 和 password（其实可以传个对象过去的）
     * 返回Boolean类型，以判断是否注册成功
     * 要连接数据库，
     * 首先判断是否已经存在该用户 ----运用finduser(uname) 判断
     * finduser(uname) 其实可以放在SqlUserBean里的
     *
     */


    //注册
    public boolean register(String uname, String upswd) throws Exception {
        UserBean user = new UserBean();

        Connection conn = JDBCUtils.dbOpen();//打开连接数据库
        user.setUsername(uname);
        user.setUserpassword(upswd);

        int num = 0;//设置是否有更新到表中
        try {
            conn = JDBCUtils.dbOpen();
            String sql = "insert into t_user (user_name,user_password,banlance) " + "values(?,?,?)";//插入语句
            //判断是否已有此用户
            if (finduser(uname) == false) {
                System.out.println("已存在");
                return false;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);//执行sql语句
            // 分别set，更新到数据库中
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getUserpassword());
            stmt.setDouble(3, moneyBean.getMoney());//初始值为0
            // 更新方法
            num = stmt.executeUpdate();
            conn.close();//关闭数据库
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (num != 0) {
            System.out.println("ok了");
            return true;
        }
        System.out.println("没有注册成功");
        return false;
    }


    /**
     * 判断用户是否存在,在业务层也需要该方法，用来判断是否存在该用户
     * @param name
     * @return boolean，当不存在返回true
     * @throws SQLException
     */


// 找到 用户名，判断用户是否存在
    public boolean finduser(String name) throws SQLException {
        Connection conn = JDBCUtils.dbOpen();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "SELECT * FROM t_user WHERE user_name='" + name + "'";
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //进入while循环，遍历整张表
                if (name.equals(rs.getString("user_name")))//限定条件
                    return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        return true;
    }


//登录功能

    /**
     * 打开数据连接，判断相应的用户名和密码是否正确
     * @param uname 用于拿到登陆的用户名
     * @param upswd 用于拿到登录的密码
     * @return Boolean ,以true和false来判断是否登陆成功
     */
    public boolean login(String uname, String upswd) throws Exception {
        Connection conn = JDBCUtils.dbOpen();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "SELECT * FROM t_user WHERE user_name='" + uname + "'and  user_password='" + upswd + "'";
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (uname.equals(rs.getString("user_name")) &&
                        upswd.equals(rs.getString("user_password")))//判断
                    return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("用户或密码错误");
        return false;
    }


    //查询余额功能

    /**
     * 以userbean 作为对象传入，查询数据库中的user表，得到相应的金额，
     * 为何不用moneybean ，因为moneybean 不能存放持久数据
     * @param user
     * @return double 金额
     * @throws Exception
     */

    public double getBalance(UserBean user) throws Exception {//获取余额
        String uname = user.getUsername();
        Connection conn = JDBCUtils.dbOpen();
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
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("查询余额");
        moneyBean.setMoney(money);// 设入到对应的当前的moneybean 中
        moneyBean.getMoney();
        return money;
    }

    /**
     * 存款功能，利用数据库的插入语句，设置金额
     * 此时要调用查询余额的方法，得到该用户账户里之前的初始金额，进行增加
     * @param add
     * @param user
     * @throws Exception
     */


   // 存款
    public void deposit(int add, UserBean user) throws Exception{
        String uname=user.getUsername();
        MoneyBean moneyBean = MoneyBean.getInstance();
        double zhiqian = getBalance(user);//得到之前账户里的金额
            double money = moneyBean.getMoney() + add ;
        System.out.println("存款");
        Connection conn = JDBCUtils.dbOpen();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "update t_user set banlance= '" + money + "' where user_name='" + uname + "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);//更新
            depoDetail(user, add);
            moneyBean.setMoney(money);
            System.out.println("存入之后"+moneyBean.getMoney());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
    }


    /**
     *  取款功能，于存款类似：
     *  利用数据库的插入语句，设置金额
     *  此时要调用查询余额的方法，得到该用户账户里之前的初始金额 ，进行减去
     *  、此时要进行判断 是否金额不足，
     * @param user
     * @param minus
     * @return Boolean 成功时返回true;
     * @throws AccountOverDrawnException
     * @throws SQLException
     */

    // 取款：
    public boolean withdrawal(UserBean user, int minus) throws AccountOverDrawnException, SQLException {
        String uname = user.getUsername();
        MoneyBean moneyBean = MoneyBean.getInstance();
        double beforemoney = 1;// 定义变量，存放getBalance(user) 的值
        try {
            System.out.println(uname+"之前的钱是 "+getBalance(user));
            beforemoney = getBalance(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(beforemoney);
        if (beforemoney < minus) {
            System.out.println("余额不足");
            return false;
        }
        double money = beforemoney - minus;
        System.out.println("取款");
        Connection conn = JDBCUtils.dbOpen();//进行数据库操作
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "update t_user set banlance= '" + money + "' where user_name='" + uname + "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);
            withdrawlDetail(user, minus);// 取款操作 方法的调用，
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println("结果的钱是(sql)"+money);
        moneyBean.setMoney((int) (moneyBean.getMoney()) - minus);//账户里的钱减去取款数，再存到账户里头
        System.out.println("目前"+uname+" 账户余额 为"+moneyBean.getMoney());
        System.out.println(moneyBean.getMoney());
        System.out.println("取款成功"); //打印输出余额
        return true;
    }

    /**
     *  转账功能，设置两个形参，一个为接收金额的用户名，一个为转账金额
     *  本来是要判断的，只是测试要显示信息，所以测试层 我也做了相应判断
     *  判断1 金额是否足够，
     *      2用户是否存在
     * @param uname
     * @param money
     * @return
     * @throws Exception
     */

   //转账
    public boolean tranfer(String uname, double money) throws Exception {

        UserBean user= new UserBean();
        System.out.println("这应该是 转账人的 名字"+user.getUsername());
        user.setUsername(uname);
           System.out.println("应该是转账人的钱"+moneyBean.getMoney());
        double myMoney = moneyBean.getMoney();
            //判断账户余额是否小于0；
            if (myMoney <= 0) {
                System.out.println("您账户中余额仅剩0元，转账失败");
                return false;
            }
            //判断转账金额是否大于账户余额，如果是，则转账失败
            if (myMoney < money) {
                System.out.println("余额不足");
                return false;
            }
                // 转账人的钱
            moneyBean.setMoney(myMoney - money);
               System.out.println("转账人的钱之后是"+moneyBean.getMoney());
            double shouzhiqian = getBalance(user);
            System.out.println("shouzhangren "+uname+"---"+ shouzhiqian);
        Connection conn = JDBCUtils.dbOpen();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "update t_user set banlance= '" + (money+shouzhiqian) + "' where user_name='" + uname + "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);
            intoDetail(uname,money);//转入明细操作的方法调用
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
        System.out.println(uname +"目前的余额是 "+ getBalance(user));
        System.out.println("取款成功"); //打印输出余额
        return true;
    }



    // 以下是对操作明细的一些方法：
    // 其实可以利用标志位的方法，这样数据库打开和关闭不会那么繁琐，
    // 利用flag 对于各种操作进行不同的赋值，
    //目前采用的是一个操作对于着一个方法
    // 其实还有一种方法，
    // 要传入 三个参数 ---sql语句，UserBean， 金额，包含在连接数据库中，这样更加体现了封装的概念


    /**
     * 传入用户名，及金额，就可以了
     * 此时利用主外键的功能，可以对相应的userId 的用户，进行添加操作
     * 数据库是插入操作
     * 此时需要找到userid ，调用模型层中的SqlUserBean中的 int  getID(UserBean user) 方法
     * @param user
     * @param add
     */

    // 由于思想是一样的，代码都有很多的重复 ，就不写注释了

    //存款明细
       public  void depoDetail (UserBean user , int add) {
           SqlUserBean sqlUserBean =new SqlUserBean();
           String uname = user.getUsername();
           int userID= 0;
           try {
               userID = sqlUserBean.getUser_id(user);
           } catch (Exception e) {
               e.printStackTrace();
           }
           System.out.println(uname + " 的 ID 是 "+ userID);//
           System.out.println("存款明细");
           Connection conn = null;
           try {
               conn = JDBCUtils.dbOpen();
           } catch (SQLException e) {
               e.printStackTrace();
           }
           ResultSet rs = null;
           Statement stmt = null;
           try {
               String sql = "insert into t_log (log_type,log_amount,userid)  values('存款' ,'"+(add)+"','"+(userID)+"')";
               stmt = (Statement) conn.createStatement();
               stmt.executeUpdate(sql);
           } catch (Exception e) {
               // TODO: handle exception
               e.printStackTrace();
           } finally {
               JDBCUtils.dbClose(conn, stmt, rs);
           }


       }



    // 取款明细：

    public  void withdrawlDetail (UserBean user , int minus) {
        SqlUserBean sqlUserBean =new SqlUserBean();
        String uname = user.getUsername();
        //double zhiqian = getBalance(user);
        int userID= 0;
        try {
            userID = sqlUserBean.getUser_id(user);;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(uname + " 的 ID 是 "+ userID);
        //double money = moneyBean.getMoney() + add ;
        System.out.println("取款明细");

        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "insert into t_log (log_type,log_amount,userid)  values('取款' ,'"+(minus)+"','"+(userID)+"')";
            // String sql = "update t_log set userid = '" + userID + "' where user_name='" + uname + "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
    }




    // 转出明细我写在了业务层，更加方便的拿到用户的uname，嗯......这样感觉也不太好的样子

    // 转入明细
    public  void intoDetail (String uname, double money) {
        SqlUserBean sqlUserBean =new SqlUserBean();
        UserBean user =new UserBean();
        int userID= 0;
        try {
            userID = sqlUserBean.getUser_id(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(uname + " 的 ID 是 "+ userID);
        //double money = moneyBean.getMoney() + add ;
        System.out.println("转入明细");

        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;

        try {
            String sql = "insert into t_log (log_type,log_amount,userid)  values('转入' ,'"+(money)+"','"+(userID)+"')";
            // String sql = "update t_log set userid = '" + userID + "' where user_name='" + uname + "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }


    }


    // 转出明细


















    }

















