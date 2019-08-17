/**
 * 自定义异常处理的类---取款金额不足
 * @author hahaha
 */

package com.cx.util;

public class AccountOverDrawnException extends Exception {
    public AccountOverDrawnException()
    {
        super();//默认调用父类无参的构造方法
    }
    public AccountOverDrawnException(String msg)
    {
        super(msg);
    }


}
