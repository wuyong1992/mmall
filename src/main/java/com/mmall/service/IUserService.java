package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * 用户接口
 * Created by wuyong on 2017/6/18.
 */

public interface IUserService {

    ServerResponse<User> login(String username, String password);

}
