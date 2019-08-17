package com.cx.test;
import com.cx.dao.UserDao;
import com.cx.model.SqlLogBean;
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
 * 所有用户明细表
 */
    public class Detail extends JFrame {

        private JPanel contentPane;
        private JTable table;
        private String head[] = null;
        private Object[][] data = null;
        private UserDao user = new UserDao();



        public Detail(UserBean userBean) {
            setTitle("用户明细表");

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

            table = new JTable();

            table.setBorder(new LineBorder(new Color(0, 0, 0)));

            addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });


            // 为头行条目 创建一个数组
            head = new String[]{
                    "id", "用户名", "操作类型", "操作金额",};

           /* 要显示JTable组件（需要用到）TableModel接口（需要下面这个类才能实现）DefaultTableModel类
            思路:
             1，先定义一个DefaultTableModel类的对象  tableModel
              DefaultTableModel tableModel= new DefaultTableModel();
            2,在  new DefaultTableModel() 设置行和列
            3，把该对象mm加入到table里  JTable table = new JTable(mm);
            */

            DefaultTableModel tableModel = new DefaultTableModel(queryData(), head) {//queryData(), head  ---行和列
               //不可被编辑
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table.setModel(tableModel);


            scrollPane.setViewportView(table);//在表格中添加滚动条

            // 这是在网上摘抄的
            //分组布局：详细解释在操作说明里
            GroupLayout gl_contentPane = new GroupLayout(contentPane);
            //水平布局
            gl_contentPane.setHorizontalGroup(
                    gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
            );
            //垂直布局
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

        /**
         * @return
         */
        public Object[][] queryData() {
            List<SqlLogBean> list = user.queryAllUser();
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

        public static void main(String[] args) {
        UserBean userBean =new UserBean();
        Detail detail= new Detail(userBean );
        }

    }