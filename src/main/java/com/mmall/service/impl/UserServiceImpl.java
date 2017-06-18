package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 * Created by wuyong on 2017/6/18.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //验证用户名是否有效
        int resultCount =  userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //TODO MD5 password 校验

        //查询该用户
        User user = userMapper.selectLogin(username,password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码不正确");
        }
        //将该用户的密码置空，放入返回值中给前端
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登陆成功",user);
    }
}
