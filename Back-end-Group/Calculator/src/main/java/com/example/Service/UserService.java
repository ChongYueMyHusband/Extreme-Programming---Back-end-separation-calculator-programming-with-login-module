package com.example.Service;

import com.example.Object.Result;
import com.example.Object.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    Result<User> register(User user);

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return 登录结果
     */
    Result<User> login(User user);
    Result<User> update(User user);


}