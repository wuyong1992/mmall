package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * 用户接口
 * Created by wuyong on 2017/6/18.
 */

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);

    public ServerResponse selectQuestion(String username);

    public ServerResponse<String> CheckAnswer(String username, String question, String answer);

    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);

    public ServerResponse<String> restPassword(String passwordOld,String passwordNew, User user);

    public ServerResponse<User> updateInformation(User user);

    public ServerResponse<User> getInformation (Integer userId);
}
