package com.cx.manager;

import com.cx.dao.BankDaoInterface;
import com.cx.factory.UserDaoFactory;
import com.cx.model.MoneyBean;
import com.cx.model.SqlUserBean;
import com.cx.model.UserBean;
import com.cx.util.AccountOverDrawnException;
import com.cx.util.InvalidDepositException;
import com.cx.util.JDBCUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerImpl implements ManagerInterface {

    MoneyBean moneyBean = MoneyBean.getInstance();
    UserBean userBean = new UserBean();
    SqlUserBean sqlUserBean =new SqlUserBean();
    private BankDaoInterface userdao = null;

    //单例模式的实现
    //1：私有的静态的当前类对象作为属性
    private static ManagerImpl instance;

    //2：私有的构造方法
    private ManagerImpl() throws Exception {
        userdao = UserDaoFactory.getInstance().getBankDao();
    }

    //3：公有的静态的当前对象类型的静态的方法 返回当前值
    //添加锁机制，让业务层更加安全
    public static synchronized ManagerImpl getInstance() throws Exception {
        if (instance == null) {
            instance = new ManagerImpl();
        }
        return instance;
    }


    /**
     * 调用持久层的方法，做一些简单的判断
     * @param uname
     * @param upswd
     * @return boolean
     */
    //注册功能
    public boolean register(String uname, String upswd) {
        try {
            if (userdao.register(uname, upswd)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *  验证用户是否登陆成功，并且抛出相应的异常
     * @param uname
     * @param upswd
     * @return boolean
     */
    //登录功能
    public boolean login(String uname, String upswd) {
        try {
            if (userdao.login(uname, upswd)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param add
     * @param user
     * @throws InvalidDepositException
     * @throws SQLException
     */

    //存款功能
    public void deposit(int add, UserBean user) throws InvalidDepositException, SQLException {
        try {
            userdao.deposit(add, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 相当于查询
     * @param user
     * @return
     */

    //查询余额
    public  double  get(UserBean user)  {
        try {
            userdao.getBalance(user);
        }catch (Exception  e){
            e.printStackTrace();
        }
        return moneyBean.getMoney();

    }


    /**
     * 判断是否存在该用户
     * @param uname
     * @return boolean
     */

    // 判断用户是否存在
    public boolean exist (String uname)  {
        boolean f=false;
        try {
            f = userdao.finduser(uname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
         if(f==true){
             System.out.println("没有杰个人");
             return  false;
         }
         else return true;

         /* int t=0;
        try {
            if (userdao.finduser(uname)) {
                System.out.println("没有杰个人");
                t=1;
            } else {
                t=0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(t==0){
            return  true;//存在
        }
        else  return false;// 不存在*/

    }


    // 以下两个方法好像这层不合适


  // 转出明细 -----因为要用到 转账人的id，将它 放在业务层 ，更加方便的拿到数据
    public  void outDetail (String uname, double money) {
        UserBean user = new UserBean();
            user.setUsername(uname);
            System.out.println("转账人的name------------"+user.getUsername());
        int userID = 0;
        try {
            userID = sqlUserBean.getUser_id(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(uname + " 的 ID 是 " + userID);
        System.out.println("转出明细");
        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "insert into t_log (log_type,log_amount,userid)  values('转出' ,'" + (money) + "','" + (userID) + "')";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }
    }


    // 更新转账人的数据库
    public void updateTransfer(String name,double afterMoney){
        Connection conn = null;
        try {
            conn = JDBCUtils.dbOpen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        Statement stmt = null;
        try {
            String sql = "update t_user set banlance= '" + afterMoney + "' where user_name='" + name+ "'";
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(sql);
            //此时的moneybean ====指向 转账人的 也就是 userName.getText()
            moneyBean.setMoney(afterMoney);
            System.out.println(" 转账之后 "+moneyBean.getMoney());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtils.dbClose(conn, stmt, rs);
        }

    }


























    //取款功能
    public void withdrawal(UserBean user,int minus) throws AccountOverDrawnException,SQLException {
        /**
         *  @param  minus  取款时的金额
         * */
        if (userdao.withdrawal(user,minus)==true){
            System.out.println("取款成功");
        }
        else System.out.println("取款失败");
    }

    //查询功能
    /**
     * @param
     */


    //转账功能
    //调用bankDao 中的转账功能
   public  void transfer(String uname,double money) {
         try{
       if( (userdao.tranfer(uname, money))==false){
           System.out.println(" zhuanz shibai ");
       }
       else  System.out.println("ok");
    }catch(Exception  e){
             e.printStackTrace();
         }
    }

    //退出系统
    /**
     * @param uname
     */
    public void exitSystem(String uname) { //退出

        System.exit(0);

    }

}


