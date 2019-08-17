package com.cx.test;

import com.cx.manager.ManagerImpl;
import com.cx.model.SqlUserBean;
import com.cx.model.UserBean;

import javax.swing.*;
import java.awt.event.*;

public class Manger {
    ManagerImpl managerlmpl;

    {
        try {
            managerlmpl = ManagerImpl.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //BankDaoImpl bankDao;
     JFrame frame;
     JLabel label;
     JButton button1;
     JButton button2;
     JButton button4;
     JButton button3;
     JTextArea area;



    // 设计界面


    public Manger(UserBean userBean) {
        // 添加面板
         String name =userBean.getUsername();
        System.out.println(userBean.getUsername());
        frame =new JFrame();
        label =new JLabel(name+"管理员，您好！"+"\n"+"   您可以进行下一步操作        ");
        area =new JTextArea(20,10);

        button1 =new JButton("   查询明细    ");
        button2 =new JButton("   冻结账户    ");
        button3 =new JButton("   解除冻结    " );
        button4=new JButton("     退出     ");

        JPanel panel =new JPanel();
        JPanel panel1 =new JPanel();
        panel.add(label);

        panel.add(button1);//查询明细
        panel.add(button2);//冻结账户
        panel.add(button3);//解除冻结
        panel.add(button4);//退出




        frame.add(panel1);
        frame.add(panel);
        frame.setSize(350, 375);
        frame.setResizable(false);//大小不可变
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        button1.addMouseListener(new MouseAdapter() {  // 查询明细
            @Override
            public void mouseClicked(MouseEvent e) {
                if(name==null){
                    JOptionPane.showMessageDialog(null, "请先登录");
                    return;
                }
                else {
                    new Detail(userBean);
                }
            }
        });
        button2.addMouseListener(new MouseAdapter() {  //冻结账户
            @Override
            public void mouseClicked(MouseEvent e) { if(name==null){
                JOptionPane.showMessageDialog(null, "请先登录");
                return;
            }
            else {
                new Lock(userBean);
            }
            }
        });


        button3.addMouseListener(new MouseAdapter() { //解除冻结
            @Override
            public void mouseClicked(MouseEvent e) {
                if(name==null){
                    JOptionPane.showMessageDialog(null, "请先登录");
                    return;
                }
                else {
                    new RelieveLock(userBean);
                }
            }
        });
        button4.addMouseListener(new MouseAdapter() {// 退出
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                System.exit(0);

            }
        });


    }












    class  Lock extends JFrame implements ActionListener {

        JTextField field = new JTextField("--输入要冻结的用户名--",30);
        // 不能 冻结 管理员的
        JButton button1 =new JButton("   确定   ");
        JButton button2 =new JButton("   取消   ");
         JButton button3 =new JButton("    返回    ");

         JPanel panel =new JPanel();
         JFrame frame=new JFrame();

         Lock( UserBean userBean){
             super("冻结账户");
             panel.add(field);
             panel.add(button1);
             panel.add(button2);
             panel.add(button3);
             frame.add(panel);
             frame.setSize(350, 250);
             frame.setResizable(false);//大小不可变
             //frame.pack();
             frame.setVisible(true);
            // frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
             addWindowFocusListener(new WindowAdapter() {
                 @Override
                 public void windowClosing(WindowEvent e) {
                     System.exit(0);
                 }
             });
             field.addMouseListener(new MouseAdapter() {//一点击用户名会清空内容
                 public void mouseClicked(MouseEvent arg0) {
                     if (field.getText().equals("--输入要冻结的用户名--")) {
                         field.setText("");
                     }
                 }
             });

             button2.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     field.setText("");
                 }
             });

             button3.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     frame.setVisible(false);
                 }
             });

             button1.addActionListener(this);
         }

        @Override
        public void actionPerformed(ActionEvent e) {
            UserBean userBean= new UserBean();
            userBean.setUsername(field.getText());
            SqlUserBean sqlUserBean =new SqlUserBean();
            if(sqlUserBean.getUser_flag(userBean)==0){
                System.out.println(userBean.getUsername()+"是管理员呀");
                JOptionPane.showMessageDialog(null,userBean.getUsername()+"是管理员呀");
                return;
            }
            sqlUserBean.setLockJudeg(userBean, 2);
            int lockJudeg =sqlUserBean.getLockJudeg(userBean);
             System.out.println(userBean.getUsername()+"该账户已被管理员冻结");
             JOptionPane.showMessageDialog(null,userBean.getUsername()+"该账户已被管理员冻结");
             System.out.println("此时"+userBean.getUsername()+"的lockJudeg = "+lockJudeg);
        }
    }


    class  RelieveLock extends  JFrame implements ActionListener {

        JTextField field = new JTextField("-----输入要解除冻结的用户名----",25);
        // 不能 冻结 管理员的
        JButton button1 = new JButton("确定");
        JButton button2 = new JButton("取消");
        JButton button3 = new JButton("返回");

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();

        RelieveLock(UserBean userBean) {
            super("解除冻结");
            panel.add(field);
            panel.add(button1);
            panel.add(button2);
            panel.add(button3);
            frame.add(panel);
            frame.setSize(350, 250);
            frame.setResizable(false);
            //frame.pack();
            frame.setVisible(true);
           // frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            addWindowListener(new WindowAdapter() {// 关闭窗口
                public void windowClosing(WindowEvent wevent) {
                    System.exit(0);
                }
            });

            field.addMouseListener(new MouseAdapter() {//一点击用户名会清空内容
                public void mouseClicked(MouseEvent arg0) {
                    if (field.getText().equals("-----输入要解除冻结的用户名----")) {
                        field.setText("");
                    }
                }
            });

            button2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    field.setText("");
                }
            });

            button3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.setVisible(false);
                }
            });


            button1.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //要判断是否已经被冻结

            UserBean userBean = new UserBean();
            userBean.setUsername(field.getText());
            System.out.println(userBean.getUsername());
            String name = userBean.getUsername();
            SqlUserBean sqlUserBean = new SqlUserBean();

            if (!managerlmpl.exist(name)) {
                JOptionPane.showMessageDialog(null, field.getText() + "用户不存在");
                field.setText("");

            } else {
                if(sqlUserBean.getUser_flag(userBean)==0){ //管理员不能冻结
                    System.out.println(userBean.getUsername()+"是管理员呀");
                    JOptionPane.showMessageDialog(null,userBean.getUsername()+"是管理员呀");
                    return;
                }
                if (sqlUserBean.getLockJudeg(userBean) == 1) {
                    System.out.println(userBean.getUsername() + "该用户不需要被解除冻结");
                    JOptionPane.showMessageDialog(null, userBean.getUsername() + "该用户不需要被解除冻结");
                } else {
                    // userBean.setLockJudege(0);
                    sqlUserBean.setLockJudeg(userBean, 1);//解除冻结
                    int lockJudeg = sqlUserBean.getLockJudeg(userBean);
                    System.out.println(lockJudeg + "是几呀 这是解除冻结的");
                    System.out.println(userBean.getUsername() + "该账户已被管理员解除冻结");
                    JOptionPane.showMessageDialog(null, userBean.getUsername() + "该账户已被管理员解除冻结");
                }
            }
        }

    }








  public  static void main(String [] args) {
      UserBean user = new UserBean();

      new Manger(user);

  }

}
