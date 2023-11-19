package com.example.extreamprogrammingtestone;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import HTTPUtilsPackage.LoginAsyncTask;
import HTTPUtilsPackage.LoginHTTPUtil;
import HTTPUtilsPackage.RegisterAsyncTask;

public class RegisterActivity extends Activity implements RegisterAsyncTask.OnRegisterResultListener{
    private Button register2;
    private EditText id;
    private EditText pwd_1;
    private EditText pwd_2;
    private EditText emails;

    private String result;
    private String username;
    private String pwd1;
    private String email;
    private String pwd2;
    private int ResultCode = 2;
    private final String TAG = "RegisterActivity";
    private SendRegisterMqttUtil sendRegisterMqtt; // 声明成员变量

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register2 = (Button) findViewById(R.id.regester2);
        id = (EditText) findViewById(R.id.id_edit);
        pwd_1 = (EditText) findViewById(R.id.passward_edit);
        pwd_2 = (EditText) findViewById(R.id.pass_edit_1);
        emails = (EditText) findViewById(R.id.email_edit);


        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = id.getText().toString().trim();
                email = emails.getText().toString().trim();
                pwd1 = pwd_1.getText().toString().trim();
                pwd2 = pwd_2.getText().toString().trim();

                if (!pwd1.equals(pwd2)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "两次输入密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    try {

                        // 创建 JSON 对象并添加数据
                        JSONObject accountJson = new JSONObject();
                        try {
                            accountJson.put("id", username);
                            accountJson.put("password", pwd1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // 将 JSON 对象转换成字符串
                        String data = accountJson.toString();
//                        String data = "id=" + username + "&password=" + pwd1;
                        Log.i(TAG, data); // 传输成功，传输内容：{"id":"123","password":"123"}
//                        sendRegisterMqtt = new SendRegisterMqttUtil(data, RegisterActivity.this);
//                        sendRegisterMqtt.sendAccount();

                        // 使用HTTP进行数据传输
                        // 创建一个新的 RegisterAsyncTask 实例
                        // 这个错误通常发生在一个 AsyncTask 实例已经被执行过一次后，尝试再次执行的情况下。
                        // 在你的代码中，你创建了一个 RegisterAsyncTask 实例，并在点击事件中执行了它。
                        // 由于 AsyncTask 实例只能执行一次，再次尝试执行相同的实例会导致这个错误。
                        //为了解决这个问题，你可以创建一个新的 RegisterAsyncTask 实例，然后执行它。确保每次点击按钮时，都使用一个新的实例。
                        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask();
                        registerAsyncTask.setCallback(RegisterActivity.this);

                        // 执行新的实例
                        registerAsyncTask.execute(username, pwd1);
                        // 成功对接：输出的可能形式 ; 已添加至数据库中
                        // {"message":"该用户名已存在！","success":false,"data":null}result
                        // {"message":"注册用户成功！","success":true,"data":{"id":0,"userName":null,"password":"fan123123",
                        // "age":0,"idiograph":null,"account":"2387171466","sex":null}}result

                        // 解绑服务连接
                        if (sendRegisterMqtt != null) {
                            sendRegisterMqtt.disconnect(); // 自定义一个 disconnect 方法，用于断开 MQTT 连接
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑服务连接并断开 MQTT 连接
        if (sendRegisterMqtt != null) {
            sendRegisterMqtt.disconnect();
        }
    }


    @Override
    public void onRegisterResult(boolean isSuccess) {
        // Handle the registration result
        if (isSuccess) {
            // Registration successful, perform UI transition or other actions
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // Registration failed, show a toast or other error message
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

