package com.example.extreamprogrammingtestone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    // 还需要实现 今日鸡汤、动态日历

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button scientificCalculator = (Button) findViewById(R.id.ScientificCalculator);
        Button interestRateCalculator = (Button) findViewById(R.id.InterestRateCalculator);
        Button userInfo = (Button) findViewById(R.id.UserInfo);

        scientificCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(HomeActivity.this,ScientificCalculatorActivity.class);
                startActivity(intent);
            }
        });

        interestRateCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(HomeActivity.this, InterestRateCalculatorActivity.class);
                startActivity(intent);
            }
        });


        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(HomeActivity.this,PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}