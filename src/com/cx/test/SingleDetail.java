package com.cx.test;

import com.cx.dao.UserDao;
import com.cx.model.SqlLogBean;
import com.cx.model.SqlUserBean;
import com.cx.model.UserBean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 生成个人的明细表
 */
public class SingleDetail extends JFrame {

    private JPanel contentPane;
    private JTable table;
     private  JTextField field;
     private  JLabel label;
    private String head[] = null;
    private Object[][] data = null;
    private UserDao user = new UserDao();
 SqlUserBean sqlUserBean=new SqlUserBean();

    public static void main(String[] args) {
        UserBean userBean =new UserBean();

    }

    public SingleDetail(UserBean userBean) {
        String uanme = userBean.getUsername();

        //setTitle(uanme+"用户明细表");
       setTitle(uanme + "用户明细表，您当前余额为"+sqlUserBean.getBanlance(userBean));

        setBounds(100, 100, 700, 300);
        Dimension us = this.getSize();
        Dimension them = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (them.width - us.width) / 2;
        int y = (them.height - us.height) / 2;

        this.setLocation(x, y);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 700, 250);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        table = new JTable();

        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        head = new String[]{
                "id", "用户名", "操作类型", "操作金额",};


        DefaultTableModel tableModel = new DefaultTableModel(queryData(userBean), head) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);

        scrollPane.setViewportView(table);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(

                //gl_contentPane.createParallelGroup(Alignment.LEADING)
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
                                .addGap(66))
        );
        contentPane.setLayout(gl_contentPane);

        setResizable(false);
        setVisible(true);

    }

//生成表格数据


    public Object[][] queryData(UserBean userBean) {
        List<SqlLogBean> list = user.queryOneUser(userBean);
        data = new Object[list.size()][head.length];

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < head.length; j++) {
                data[i][0] = list.get(i).getLog_id();
                data[i][1] = list.get(i).getUser_name();
                data[i][2] = list.get(i).getLog_type();
                data[i][3] = list.get(i).getLog_amount();

            }
        }
        return data;
    }

}
