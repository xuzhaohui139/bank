
/**
 * 自定义异常处理的类---存款不可为负数
 * @author hahaha
 */

package com.cx.util;

public class InvalidDepositException  extends Exception{
    public InvalidDepositException()
    {
        super();//默认调用父类无参的构造方法
    }
    public InvalidDepositException(String msg)
    {
        super(msg);
    }


}
