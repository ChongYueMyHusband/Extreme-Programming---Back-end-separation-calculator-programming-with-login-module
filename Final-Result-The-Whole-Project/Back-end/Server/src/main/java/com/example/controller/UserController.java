package com.example.controller;


import com.example.Object.Result;
import com.example.Object.User;
import com.example.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.bytecode.BytecodeLogging.LOGGER;


@RestController
public class UserController {

    public static final String SESSION_NAME = "userInfo";

    @Autowired
    private UserService userService;

    private User user;

    private BindingResult errors ;

    @PostMapping("register")
    public Result<User> register(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        LOGGER.info("收到一条注册");
        Result<User> result;
        // 如果校验有错，返回注册失败以及错误信息
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }
        // 调用注册服务
        result = userService.register(user);
        return result;
    }
    @PostMapping ("/login")
    public Result<User> login(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        LOGGER.info("收到登录");
        Result<User> result;
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }
        System.out.println("Account of User is : "+user.getAccount());
        // 调用登录服务
        result = userService.login(user);
        // 如果登录成功，则设定session
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.getData());
        }

        return result;
    }

    @PutMapping("/PersonalInfo")
    public Result<User> update(@RequestBody User user, HttpServletRequest request) throws Exception {
        System.out.println("Account is :"+user.getAccount());
        LOGGER.info(SESSION_NAME);
        LOGGER.info("收到更改信息");
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        /*
        // 检查session中的用户（即当前登录用户）是否和当前被修改用户一致
        User sessionUser = (User) session.getAttribute(SESSION_NAME);
        if (!sessionUser.getAccount().equals(user.getAccount())) {
            result.setResultFailed("当前登录用户和被修改用户不一致，终止！");
            return result;
        }*/
        result = userService.update(user);
        // 修改成功则刷新session信息
        if (result.isSuccess()) {
            session.setAttribute(SESSION_NAME, result.getData());
        }
        return result;
    }
/*
    @SneakyThrows
    @RequestMapping("register2")
    public void register() throws MqttException {

        mqttserver.init("pub_register",1);
        MqttMessage data=mqttserver.message;
        String message = new String(data.getPayload(),"UTF-8");
        // 使用Jackson库将JSON字符串转换为User对象
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(message, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(user.getUserName());
        Result<User> result;
        // 如果校验有错，返回注册失败以及错误信息
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            System.out.println("receive failed");
            return ;
        }
        // 调用注册服务
        result = userService.register(user);
        System.out.println("receive correctly ");
        mqttserver.sendMQTTMessage("pub_register",result.getMessage(),0);
        return;
    }


    @SneakyThrows
    @PostMapping("/login2")
    public Result<User> login2(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        mqttserver.init("pub_account", 0);
        Result<User> result;
        MqttMessage data=mqttserver.message;
        String message = new String(data.getPayload(),"UTF-8");
        // 使用Jackson库将JSON字符串转换为User对象
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(message, User.class);
        // 如果校验有错，返回登录失败以及错误信息
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            mqttServer.sendMQTTMessage("pub_account", result.getMessage(), 0);
            return result;
        }
        // 调用登录服务
        result = userService.login(user);
        // 如果登录成功，则设定session
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.getData());
        }
        String ReturnMessage= "id="+result.getData().getUserName()+"&password="+result.getData().getPassword();
        mqttServer.sendMQTTMessage("pub_account",ReturnMessage,0);
        return result;
    }
    @PutMapping("/update2")
    public Result<User> update2(@RequestBody User user, HttpServletRequest request) throws Exception {
        mqttserver.init("pub_personalInfo", 0);
        MqttMessage data=mqttserver.message;
        String message = new String(data.getPayload(),"UTF-8");
        // 使用Jackson库将JSON字符串转换为User对象
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(message, User.class);
        Result<User> result = new Result<>();
        result = userService.update(user);
        mqttServer.sendMQTTMessage("pub_personalInfo", result.getMessage(), 0);
        return result;
    }*/
    /**
     * 用户登出
     *
     * @param request 请求，用于操作session
     * @return 结果对象
     */
    @GetMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        Result<Void> result = new Result<>();
        // 用户登出很简单，就是把session里面的用户信息设为null即可
        request.getSession().setAttribute(SESSION_NAME, null);
        result.setResultSuccess("用户退出登录成功！");
        return result;
    }

}