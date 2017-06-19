package com.mmall.common;

/**
 * 常量类
 * Created by wuyong on 2017/6/18.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    //通过内部的接口类分组
    public interface Role {
        int ROLE_CUSTOMER = 0;  //普通用户
        int ROLE_ADMIN = 1;  //管理员
    }

}
