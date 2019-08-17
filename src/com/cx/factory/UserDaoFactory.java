package com.cx.factory;


import com.cx.dao.BankDaoInterface;


import java.io.InputStream;
import java.util.Properties;

/**
 * 采用单例和同步
 * 功能说明：完成业务层和持久层的动态装配，消除业务层和持久层的耦合性
 * 1.读取properties文件
 * 2.通过反射创建对象
 * 3.把持久层装配給业务层
 * 4.在业务层的方法中可以调用持久层相应的方法
 */
public class UserDaoFactory {
    private  static BankDaoInterface userDao ;
    // private BankDaoInterface bankDao ;
    // 使用懒汉式创建单例
    private static UserDaoFactory instance;
    private   UserDaoFactory() throws  Exception {
        Properties prop = new Properties();
        InputStream in =UserDaoFactory.class.getClassLoader().getResourceAsStream("dao.properties");
       // FileInputStream in = new FileInputStream(new File("dao.properties"));
        prop.load(in);
        in.close();
        String daoClassname = prop.getProperty("bankDao");
        //利用反射拿到类名
        /*Class clazz=Class.forName(daoClassname);
        Object obj = clazz.newInstance();
        userDao = (BankDaoInterface)obj;*/
        userDao =(BankDaoInterface)Class.forName(daoClassname).newInstance();
    }
    //单例并加锁，让操作的为同一个仓库，更加安全
    public  static synchronized UserDaoFactory getInstance() throws  Exception{
        if(instance ==null){
            instance =new UserDaoFactory();
        }
        return  instance;
    }
    // 使用工厂设计模式来获取Dao中的方法
    public BankDaoInterface getBankDao() {
        return userDao;
    }
}


