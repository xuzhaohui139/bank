

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc {


    //传入SQL语句，返回值为boolean类型，表示更新是否成功
    public static boolean update(String sql) {
        Connection connection = null;
        Statement statement= null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.连接数据库
            connection = DriverManager.getConnection(
                    //"jdbc:mysql://127.0.0.1:3306/banksvs?serverTimezone=GMT", "root", "123");
                    "jdbc:mysql://localhost:3306/banksvs?serverTimezone=GMT&useSSL=false","root","123");
            //3.创建Statement对象
            statement= connection.createStatement();
            //4.执行SQL语句，并返回更新操作是否成功，成功返回true，否则返回false
            return statement.executeUpdate(sql)>0;
            //return statement.executeQuery(sql) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //5.释放资源(注意顺序)
            try {
                if (statement!=null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
   // public static void main(String [] args) {
        //String sql="select * from t_user";
        //String sql = "insert into user_infor (id,user_name,password) values ('11111','00000',
        String sql = "insert into t_user (user_name,user_password) values ('11111','00000')";


        //String sql = "update user_infor set id='aaa',user_name='bbb',password='ccc' where user_name='0'";
        //String sql = "update t_user set user_name='1ggg',user_password='0000' where user_name='11111'";

        //String sql = "delete from user_infor where user_name = '1'";
        //String sql = "delete from t_user where user_name = '1ggg' and user_password=00000";
     //   System.out.println(update(sql));

    //}

}

