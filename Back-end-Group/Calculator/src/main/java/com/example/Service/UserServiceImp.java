package com.example.Service;


import com.example.DAO.UserDAO;
import com.example.Object.Result;
import com.example.Object.User;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

;


@Component
public class UserServiceImp implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();
        // 先去数据库找用户名是否存在
        User getUser = userDAO.getByAccount(user.getAccount());
        if (getUser != null) {
            result.setResultFailed("该用户名已存在！");
            return result;
        }
        // 存入数据库
        userDAO.add(user);
        // 返回成功消息
        result.setResultSuccess("注册用户成功！", user);
        return result;
    }

    @Override
    public Result<User> login(User user) {
        Result<User> result = new Result<>();
        // 去数据库查找用户
        User getUser = userDAO.getByAccount(user.getAccount());
        if (getUser == null) {
            result.setResultFailed("用户不存在！");
            return result;
        }
        if (!getUser.getPassword().equals(user.getPassword())) {
            result.setResultFailed("用户名或者密码错误！");
            return result;
        }
        // 设定登录成功消息
        result.setResultSuccess("登录成功！", getUser);
        return result;
    }
    @Override
    public Result<User> update(User user)  {
        Result<User> result = new Result<>();
        // 去数据库查找用户
        User getUser = userDAO.getByAccount(user.getAccount());
        if (getUser == null) {
            result.setResultFailed("用户不存在！");
            return result;
        }
        // 检测传来的对象里面字段值是否为空，若是就用数据库里面的对象相应字段值补上
        if (!StringUtils.isEmpty(user.getPassword())) {

            user.setPassword(user.getPassword());
        }

        // 存入数据库
        userDAO.update(user);
        result.setResultSuccess("修改用户成功！", user);
        return result;
    }


}