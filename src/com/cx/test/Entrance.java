package com.cx.test;

import com.cx.manager.ManagerImpl;
import com.cx.model.MoneyBean;
import com.cx.model.SqlUserBean;
import com.cx.model.UserBean;
import com.cx.util.AccountOverDrawnException;
import com.cx.util.InvalidDepositException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Entrance extends JFrame implements ActionListener {
    ManagerImpl managerlmpl;
    SqlUserBean sqlUserBean= new SqlUserBean();
    {
        try {
            managerlmpl = ManagerImpl.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    UserBean userBean = new UserBean();


    //显示组件
    private JLabel label1;
    private JLabel label2;
    JTextField userName;
     JTextField passtext;
    private JFrame frame;
    private String name;
    private JButton login;
    private JButton register;
    private JPanel panel1;
    private JPanel panel2;

//构造方法，添加组件
    Entrance() {
        super("巴拉巴拉的简易银行"); //标题
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        label1 = new JLabel("用户名：");
        userName = new JTextField("--请输入用户名--", 20);
        userBean.setUsername(userName.getText());
        name = userName.getText();

        label2 = new JLabel("  密码：  ");
        passtext = new JPasswordField("", 20);
        userBean.setUserpassword(passtext.getText());

        login = new JButton("登录");
        login.setBounds(105, 180, 60, 20);

        register = new JButton("注册");
        register.setBounds(105, 180, 60, 20);

        userName.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {// 一点击用户名会清空内容
                if (userName.getText().equals("--请输入用户名--")) {
                    userName.setText("");
                }
            }
        });

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {// 点击注册，会跳到 注册页面
               new Register();
               hidFun();
            }
        });

         //添加组件，显示组件
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(label1);
        panel1.add(userName);
        panel1.add(label2);
        panel1.add(passtext);
        panel1.add(login);
        login.addActionListener(this);//登录实现监听
        panel1.add(register);

        Panel p = new Panel();
        p.setLayout(new BorderLayout());//边界布局
        p.add(panel1, BorderLayout.CENTER);
        add(p);
        setBounds(300, 200, 300, 450);
        setResizable(false);//大小不可以变
        setVisible(true);
    }

    //对登录的事件监听

    /**
     * 做判断：
     * 1 不可为空
     * 2 用户类型
     * 3 用户是否被冻结
     * 4 用户名或密码 是否错误
     */
    public void actionPerformed(ActionEvent arg0) {
        UserBean userBean =new UserBean();
        SqlUserBean sqlUserBean =new SqlUserBean();
        String name = userName.getText();
        String pass = passtext.getText();
        userBean.setUsername(name);
        if ((userName.getText()).equals("") || (passtext.getText()).equals("")) {
            JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
        }
        System.out.println( "拿到的是 flag："+sqlUserBean.getUser_flag(userBean));// 1 为普通用户， 0 为管理员
        System.out.println( "拿到的是 lock，"+sqlUserBean.getLockJudeg(userBean));// 2为已被冻结 ；1为没有被冻结：
        if (managerlmpl.login(name, pass)) {
            System.out.println("欢迎" + name + "登录该系统！");//登录成功
            System.out.println("登录成功");
            if(sqlUserBean.getUser_flag(userBean)==1){
                // 为普通用户
                   JOptionPane.showMessageDialog(null, userName.getText() + "登录成功");
                    //hidFun();
            // 判断是否冻结
                if(sqlUserBean.getLockJudeg(userBean)==2){
                                      JOptionPane.showMessageDialog(null, "该账户已被管理员冻结");
                                      }
                System.out.println(userBean.getUsername());
                new Index(userBean);
                hidFun();
            }
            // 管理员页面
            if(sqlUserBean.getUser_flag(userBean)==0){
                JOptionPane.showMessageDialog(null, userName.getText() + "管理员登录成功");
                hidFun();
                System.out.println(userBean.getUsername());
                new Manger(userBean);// 管理元的专属页面
                hidFun();
                }
        }
        else {
            JOptionPane.showMessageDialog(null, "密码或用户名错误");
            System.out.println("密码或用户名错误！请重新输入");
            userName.setText("");
            passtext.setText("");
        }

    }



//注册：
 class Register extends  JFrame implements  ActionListener {
     UserBean userBean = new UserBean();
     private JLabel label1;
     private JLabel label2;
     private JLabel label3;
     JTextField userName;
     JTextField passtext;
     JTextField repass;
     private JFrame frame;
     private JButton confirm;
     private JButton cancel;
     private JPanel panel1;

     // 注册的构造方法
     Register() {
         super("注册");
         JFrame frame = new JFrame();
         frame.setLayout(new BorderLayout());
         label1 = new JLabel("    用户名：    ");
         userName = new JTextField("--请输入用户名--", 25);

         label2 = new JLabel("     密  码       ");
         passtext = new JPasswordField("", 25);

         label3 = new JLabel("再次确认密码");
         repass = new JPasswordField("", 25);

         confirm = new JButton("确认");
         confirm.setBounds(105, 280, 60, 20);


         cancel = new JButton("取消");
         cancel.setBounds(105, 280, 60, 20);

         userName.addMouseListener(new MouseAdapter() {//一点击用户名会清空内容
             public void mouseClicked(MouseEvent arg0) {
                 if (userName.getText().equals("--请输入用户名--")) {
                     userName.setText("");
                 }
             }
         });

         repass.addMouseListener(new MouseAdapter() {//一点击确认密码框会清空内容
             @Override
             public void mouseClicked(MouseEvent e) {
                 if (repass.getText().equals("")) {
                     repass.setText("");
                 }
             }
         });

         cancel.addMouseListener(new MouseAdapter() {//清空所有数据
             @Override
             public void mouseClicked(MouseEvent e) {
                 userName.setText("");
                 passtext.setText("");
                 repass.setText("");
             }
         });

         addWindowListener(new WindowAdapter() {// 关闭窗口
             public void windowClosing(WindowEvent wevent) {
                 System.exit(0);
             }
         });
     //添加组件
         panel1 = new JPanel();
         panel1.setLayout(new FlowLayout());
         panel1.add(label1);
         panel1.add(userName);
         panel1.add(label2);
         panel1.add(passtext);
         panel1.add(label3);
         panel1.add(repass);
         panel1.add(confirm);
         panel1.add(cancel);
         Panel p = new Panel();
         p.setLayout(new BorderLayout());
         p.add(panel1, BorderLayout.CENTER);
         add(p);
         setBounds(300, 200, 400, 500);
         setResizable(false);
          setVisible(true);
         confirm.addActionListener(this);// 为确认键添加事件监听
        // System.out.println(userBean.getUsername());
     }

    /**
     * 做判断：
     *  1 不可为空
     *  2 用户是否已经被注册过
     *  3 用户密码>6 位数
     *  4 对密码的再次确认做判断
     *
     */


    //监听注册确认事件
     public void actionPerformed(ActionEvent arg0) {
         String name = userName.getText();
         String pass = passtext.getText();
         userBean.setUsername(name);
         userBean.setUserpassword(pass);

         if ((userName.getText()).equals("") || (passtext.getText()).equals("")) {
             JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
             System.out.println("用户名和密码不能为空");
             return;
         }

         if (managerlmpl.exist(userName.getText()) == false) {
             JOptionPane.showMessageDialog(null, userName.getText() + "此用户不存在");
             return;
         }
         if ((passtext.getText().length() < 6)) {
             JOptionPane.showMessageDialog(null, "密码至少超过6位数");
             System.out.println("密码至少超过6位数");
             return;
         }

         //注册成功
         if ((passtext.getText().length() >= 6)) {
             if ((passtext.getText()).equals(repass.getText())) {
                 managerlmpl.register(name, pass);
                 System.out.println("ok!");
                 JOptionPane.showMessageDialog(null, "恭喜" + userName.getText() + "注册成功");
                 JOptionPane.showMessageDialog(null, "欢迎来到" + name + "进入系统");
                 userBean.setUsername(name);
                 System.out.println(name);
                 new Entrance();
                 this.setVisible(false);
             } else {
                 JOptionPane.showMessageDialog(null, "请再次确认密码");
                 System.out.println("密码错误！请重新输入");
             }
         }
     }
 }

    /**
     * 功能有 存款，取款，查询，转账
     */
 // 登录之后进入主功能页面
     class Index extends JFrame {
         MoneyBean moneyBean = MoneyBean.getInstance();
        SqlUserBean sqlUserBean =new SqlUserBean();

         Frame frame = new Frame("简易银行主页面");
         Panel p1 = new Panel();
         JButton btn1 = new JButton("存款");

         JButton btn2 = new JButton("取款");

         JButton btn4 = new JButton("转账");

         JButton btn3 = new JButton("查询");

         JButton btn5 = new JButton("退出");
         JPanel p2 = new JPanel();

         // 有参的构造方法
         Index(UserBean userBean) {
             String name =userBean.getUsername();
           System.out.println(name);
             p2.setLayout(new GridLayout(5, 1));
             p2.add(btn1);
             p2.add(btn2);
             p2.add(btn3);
             p2.add(btn4);
             p2.add(btn5);
             frame.add(p2);
             frame.setSize(300, 600);
             frame.setResizable(false);
             frame.setVisible(true);



             // 存款-----判断是否该用户有无被冻结
             btn1.addMouseListener(
                     new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     // 判断是否冻结
                     if(sqlUserBean.getLockJudeg(userBean)==2){
                         JOptionPane.showMessageDialog(null, "该账户已被管理员冻结");
                     }else {
                         new Depo();
                     }
                 }
             });
             //查询-----只能查询个人明细表，冻结了也OK查询
             btn3.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     new Inqury(userBean);
                 }
             });

             //取款 ----先判断是否被冻结
             btn2.addMouseListener(
                     new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     if(sqlUserBean.getLockJudeg(userBean)==2){
                         JOptionPane.showMessageDialog(null, "该账户已被管理员冻结");
                     } else {
                         new WithDra();
                         //frame.setVisible(false);
                     }
                 }
             });
             //转账 --先判断是否被冻结
             btn4.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     if(sqlUserBean.getLockJudeg(userBean)==2){
                         JOptionPane.showMessageDialog(null, "该账户已被管理员冻结");
                     } else {
                         new Tranfer();
                         //frame.setVisible(false);
                     }
                 }
             });
             // 退出
             btn5.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     userBean.setUsername(userName.getText());
                     managerlmpl.get(userBean);
                        int rs =JOptionPane.showConfirmDialog(null,"确定要退出吗？");
                         if(rs==0){
                             JOptionPane.showMessageDialog(null, userBean.getUsername() + "您已安全退出该系统");
                             dispose();
                             System.exit(0);
                             hidFun();
                         }
                     frame.addWindowListener(new WindowAdapter() {
                         public void windowClosing(WindowEvent wevent) {
                             System.exit(0);
                             dispose();
                         }
                     });
                 }
             });

             frame.addWindowListener(new WindowAdapter() {//关闭窗口
                 public void windowClosing(WindowEvent wevent) {
                     System.exit(0);
                 }
             });
         }

         // 存款页面
         class Depo implements ActionListener {

             JFrame frame = new JFrame(name+"转账页面");
             JPanel panel = new JPanel();
             JLabel label = new JLabel("存款金额");
             JButton button1 = new JButton("确认");
             JButton button3 = new JButton("返回");
             JButton button2 = new JButton("取消");
             JTextField t1 = new JTextField("", 12);

             Depo() {
                 userBean.setUsername(userName.getText());
                 name = userBean.getUsername();
                 System.out.println(userBean.getUsername());
                 panel.add(label);
                 panel.add(t1);
                 panel.add(button1);//确认
                 panel.add(button3);//返回
                 panel.add(button2);//取消
                 frame.add(panel);
                 String money = t1.getText();
                 System.out.println(money);
                 frame.setSize(300, 200);
                 frame.setResizable(false);
                 frame.setVisible(true);


                 button1.addActionListener(this); //对确认事件进行监听
                 button2.addMouseListener(new MouseAdapter() {//取消--- 清空文本框
                     @Override
                     public void mouseClicked(MouseEvent e) {
                         t1.setText("");
                     }
                 });
                 button3.addMouseListener(new MouseAdapter() {//返回
                     @Override
                     public void mouseClicked(MouseEvent e) {
                         Index index = new Index(userBean);// 回到主菜单
                         frame.setVisible(false);
                        index.frame.setVisible(false);
                     }
                 });
             }

             // 对确认存款事件监听的实现
             public void actionPerformed(ActionEvent arg0) {
                String x = t1.getText();
               userBean.setUsername(userBean.getUsername());
               int add = Integer.parseInt(x);
                 try {
                     try {
                         managerlmpl.deposit(add, userBean);//调用业务层的存款方法
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                     System.out.println(add + userBean.getUsername());
                     JOptionPane.showMessageDialog(null,userBean.getUsername()+"目前余额"+
                             managerlmpl.get(userBean));//再次获得余额
                     t1.setText("");
                 } catch (InvalidDepositException ie) {//自定义异常，并非系统异常
                     //ie.printStackTrace();
                     System.out.println(ie.getMessage());
                 }
             }
         }


         //查询页面
         class Inqury {
             Inqury(UserBean userBean) {
                 userBean.setUsername(userName.getText());
                 System.out.println(userBean.getUsername());
                new SingleDetail(userBean);// 重新定义一个类 ，查询个人明细表
                 //JOptionPane.showMessageDialog(null, userBean.getUsername() + "你的余额为" + managerlmpl.get(userBean));
             }
         }
     }

     // 取款页面
     class WithDra implements ActionListener {
         JFrame frame = new JFrame("取款页面");
         JPanel panel = new JPanel();
         JLabel label = new JLabel("取款金额");
         JButton button1 = new JButton("确认");
         JButton button3 = new JButton("返回");
         JButton button2 = new JButton("取消");
         JTextField t1 = new JTextField("", 12);

         WithDra() {
             name = userBean.getUsername();
             panel.add(label);
             panel.add(t1);
             panel.add(button1);//确认
             panel.add(button3); //返回
             panel.add(button2);  //取消
             frame.add(panel);
             String money = t1.getText();
             System.out.println(money);
             frame.setSize(300, 200);
             frame.setResizable(false);
             frame.setVisible(true);

             button2.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     t1.setText("");
                 }
             });//取消--- 清空文本框

             button3.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {//返回
                     Index index = new Index(userBean);// 回到主菜单
                     frame.setVisible(false);
                     index.frame.setVisible(false);
                 }
             });

             button1.addActionListener(this);// 对确认进行监听
         }

         // 对取款确认事件监听的实现
         public void actionPerformed(ActionEvent arg0) {
             MoneyBean moneyBean = MoneyBean.getInstance();
             String x = t1.getText();
             userBean.setUsername(userName.getText());
             double money =moneyBean.getMoney();
             System.out.println("目前的余额为"+money);
            System.out.println (managerlmpl.get(userBean));
             int minus = Integer.parseInt(x);
             // 判断是否可以取款
             if(money<minus||money==0){
                 JOptionPane.showMessageDialog(null, userBean.getUsername()+"余额不足，取款失败");
                 t1.setText("");
                 return;
             }
              else {// 开始取款
                 try {
                     try {
                         managerlmpl.withdrawal(userBean, minus);//调用取款方法
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                 } catch (AccountOverDrawnException ae) {//自定义异常
                     ae.printStackTrace();
                     System.out.println(ae.getMessage());
                 }
                 //提示信息
                 JOptionPane.showMessageDialog(null, userBean.getUsername() +
                         "你已经取款" + minus + "，您当前余额" + managerlmpl.get(userBean));
                 t1.setText("");
             }
         }

     }


     //转账页面
     class Tranfer implements ActionListener {
         JFrame frame = new JFrame("转账页面");
         JPanel panel = new JPanel();
         JLabel label1 = new JLabel("转账人" + userName.getText());
         JLabel label2 = new JLabel("转账金额");
         JLabel label3 = new JLabel("收帐人");

         JButton button1 = new JButton("确认");
         JButton button3 = new JButton("返回");
         JButton button2 = new JButton("取消");
         JTextField money = new JTextField("", 12);
         JTextField people = new JTextField("", 12);

         //转账构造方法
         Tranfer() {

             userBean.setUsername(userName.getText());
             panel.add(label1);
             panel.add(label2);//转账人
             panel.add(money);
             panel.add(label3);
             panel.add(people);
             panel.add(button1);
             panel.add(button3);
             panel.add(button2);
             frame.add(panel);
             frame.setSize(300, 200);
             frame.setResizable(false);
             frame.setVisible(true);

             button2.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {//清空文本框
                     money.setText("");
                     people.setText("");
                 }
             });

             button3.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {//返回
                     Index index = new Index(userBean);
                     frame.setVisible(false);
                     index.frame.setVisible(false);
                 }
             });


             button1.addActionListener(this);//确认监听

         }
       // 处理监听：
         public void actionPerformed(ActionEvent arg0) {
             MoneyBean moneyBean = MoneyBean.getInstance();
             // 此时的钱是 登录人的金额，也就是转账人的钱
             managerlmpl.get(userBean);
             System.out.println(moneyBean.getMoney());
             // 将转账人的 为转账之前的金额 存储到一个变量中
             double beforeMoney = moneyBean.getMoney();

             String shou = people.getText();// 收帐人的 name
             String MONEY = money.getText(); // 转账金额
             userBean.setUsername(shou);

             double MinusMoney = Double.parseDouble(MONEY); // 转账金额变成double类型
             System.out.println((Double.parseDouble(MONEY)));

             if (beforeMoney < MinusMoney) { // 首次判断 ， 转账金额 是否小于 本人账户中的金额
                 JOptionPane.showMessageDialog(null, "金额不足，转账失败");
                 return;
             }
             // 判断 接收用户 是否存在
             // 调用 exist 方法
             // 判断 接收用户 是否被冻结
             System.out.println("收账人  " + people.getText());
             System.out.println("有没有这个人" + managerlmpl.exist(shou));

             if (managerlmpl.exist(people.getText()) == false) {
                 JOptionPane.showMessageDialog(null, people.getText() + "此用户不存在");
                 return;
             }
             // 判断 接收用户 是否被冻结
             if(sqlUserBean.getLockJudeg(userBean)==2){
                 System.out.println(userBean.getUsername()+ " 的账户 的 冻结 值 为 "+sqlUserBean.getLockJudeg(userBean));
                 JOptionPane.showMessageDialog(null, userBean.getUsername()+"该账户已被管理员冻结!!! 转账 失败");
                return;
             }


             // 正式进入转账

             System.out.println("开始转账");
             System.out.println("转账人现有的钱" + moneyBean.getMoney());
             System.out.println("一样" + beforeMoney);
             moneyBean.getMoney();// 此时拿到的就未转账之前的金额 相当于 beforeMoney！！！
             double afterMoney = beforeMoney - MinusMoney;// 将 转账人的金额 减去 转出的 金额 存储到 一个变量中

             // 调用转账方法
             managerlmpl.transfer(shou, Double.parseDouble(MONEY));

             // 打印出 转账人  转账之后 的目前金额
             System.out.println(userName.getText() + "之后的余额" + afterMoney);

             System.out.println(shou + " 的钱是  " + moneyBean.getMoney());// 此时的 getmoney 为收账人的钱
             System.out.println(managerlmpl.get(userBean));//  这也是收账 人的  查询

             JOptionPane.showMessageDialog(null, "转账成功    " + userName.getText() + "之后的余额" + afterMoney);
             money.setText("");
//               // 更新转账人 数据库中的金额： 用户名 为 userName.getText(),余额为 afterMoney
             managerlmpl.updateTransfer(userName.getText(), afterMoney);
             managerlmpl.outDetail(userName.getText(), Double.parseDouble(MONEY));

         }
     }
        // 一个隐藏方法
    void hidFun() {
        this.setVisible(false);
    }

    // 主方法
        public static void main(String[] args) {

           new Entrance();
        }
     }

