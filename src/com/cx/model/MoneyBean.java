package com.cx.model;
/**
 * 定义数据类
 *<DL><DT><b>功能：实现set和get</b>
 * <DD>单例模式演示-/DD></DL>
 * 运用单例模式的懒汉式模式
 * 对money的取得和存入的封装
 */

public class MoneyBean  {
    private double money;
    //懒汉式模式

    //私有的静态的当前类对象作为属性
    private static MoneyBean instance;

    //私有并有参的构造方法
    private MoneyBean(int count1)
    {

        this.money = count1;
    }
     //公有的静态的当前对象类型的静态的方法 返回当前值
    public static MoneyBean getInstance(){
        if(instance==null){
            instance=new MoneyBean(0);
        }
        return instance;
    }


    public double getMoney ()
    {
        return this.money ;
    }
    public void setMoney(double money)

    {
        this.money=money;
    }


}
