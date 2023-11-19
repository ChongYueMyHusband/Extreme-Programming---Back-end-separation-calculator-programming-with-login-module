package com.example.extreamprogrammingtestone;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import HTTPUtilsPackage.InterestRateAsyncGetHistory;
import HTTPUtilsPackage.InterestRateAsyncTask;

public class DepositLoanHistoryActivity extends AppCompatActivity implements InterestRateAsyncGetHistory.OnInterestRaterHistoryListener{
    private LinearLayout llHistoryRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_loan_history);

        llHistoryRecords = findViewById(R.id.in_history_record111);

        InterestRateAsyncGetHistory interestRateAsyncGetHistory = new InterestRateAsyncGetHistory();
        interestRateAsyncGetHistory.setCallback(DepositLoanHistoryActivity.this);
        interestRateAsyncGetHistory.execute(PersonalinfoMessage.getPsw(), PersonalinfoMessage.getUser());
    }

    private void show(List<String> historyList) {
        llHistoryRecords.removeAllViews(); // 清除之前的视图

        for (String equation : historyList) {
            Log.i("DepositLoanHistoryAc", equation);
            TextView historyItem111 = new TextView(this);
            historyItem111.setText(equation);
            historyItem111.setTextSize(30); // 设置字体大小为16sp，你可以根据需要调整大小
            // 设置文本颜色为黑色
            historyItem111.setTextColor(getResources().getColor(android.R.color.black));
            // 设置字体为粗体
            historyItem111.setTypeface(historyItem111.getTypeface(), Typeface.BOLD);
            llHistoryRecords.addView(historyItem111);
        }
    }

    @Override
    public void onCalculatorResult(String result) {
        // 接收到数据库的所有消息，记录为 result
        Log.d("DepositLoanHistoryActivity", "Result: " + result);
        // 进行数据处理
        // 去掉方括号
        String content = result.substring(1, result.length() - 1);
        // 通过逗号分割字符串，得到每个等式
        String[] equations = content.split(", ");
        // 创建历史记录列表
        List<String> historyList = Arrays.asList(equations);

        for (String equation : historyList) {
            Log.d("DepositLoanHistoryActivity", equation);
            // 测试结果正确：2 * 4 5 =90.0 ； 8 * 6 + 6 =54.0 ； 9 * 9 =81.0， 结果
        }


        // 处理获取历史记录的结果
        if (historyList != null && !historyList.isEmpty()) {
            show(historyList);
        } else {
            // 处理没有历史记录的情况
            TextView historyItem = new TextView(this);
            historyItem.setText("No History!!");
            llHistoryRecords.addView(historyItem);
        }
    }
}