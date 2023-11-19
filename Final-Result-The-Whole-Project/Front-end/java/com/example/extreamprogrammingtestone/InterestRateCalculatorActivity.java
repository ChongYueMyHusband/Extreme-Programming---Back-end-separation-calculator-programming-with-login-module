package com.example.extreamprogrammingtestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import HTTPUtilsPackage.InterestRateAsyncTask;
import HTTPUtilsPackage.RegisterAsyncTask;

public class InterestRateCalculatorActivity extends AppCompatActivity implements InterestRateAsyncTask.OnInterestListener {
    // 初始化控件
    private String Period="";

    private String type="";
    private Button enquireBtn; // 查询按钮
    private Button modifyBtn; // 修改按钮
    private Button historyBtn; // 历史按钮
    private Button calculateBtn;    // 计算按钮
    private TextView textViewContent; // TV控件
    private TextView showViewContent;
    private EditText editTextContent; // ET控件
    private EditText writeInterest; // ET控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_rate_calculator);

        enquireBtn = (Button)findViewById(R.id.Enquire);
        modifyBtn = (Button)findViewById(R.id.Modify);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
        editTextContent = (EditText)findViewById(R.id.money);
        historyBtn = (Button)findViewById(R.id.historyDepositLoan);
        showViewContent = (TextView)findViewById(R.id.showView);
        writeInterest = (EditText)findViewById(R.id.writeInterest);

        // 获取两个 Spinner 控件
        Spinner spinnerDepositLoan = findViewById(R.id.spinnerDepositLoan);
        Spinner spinnerDuration = findViewById(R.id.spinnerDuration);

        // 为第一个 Spinner 设置适配器
        ArrayAdapter<CharSequence> depositLoanAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.deposit_loan_options,
                android.R.layout.simple_spinner_item
        );
        depositLoanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepositLoan.setAdapter(depositLoanAdapter);

        // 为第二个 Spinner 设置适配器
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.duration_options,
                android.R.layout.simple_spinner_item
        );
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(durationAdapter);

        // 添加第一个 Spinner 的选择监听器
        spinnerDepositLoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 处理第一个 Spinner 的选择
                String selectedDepositLoan = parentView.getItemAtPosition(position).toString();
                // 在这里执行相应的逻辑，例如显示选中的选项
                Log.i("InterestRateCalculatorActivity", selectedDepositLoan);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 未选择任何项时的处理
            }
        });

        // 添加第二个 Spinner 的选择监听器
        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 处理第二个 Spinner 的选择
                String selectedDuration = parentView.getItemAtPosition(position).toString();
                // 在这里执行相应的逻辑，例如显示选中的选项
                Log.i("InterestRateCalculatorActivity", selectedDuration);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 未选择任何项时的处理
            }
        });

        //  Enquire按钮的点击事件
        enquireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理查询按钮点击事件
                // 这里可以获取选中的 Spinner 内容，然后将内容发送给后端
                String selectedDepositLoan = spinnerDepositLoan.getSelectedItem().toString();
                String selectedDuration = spinnerDuration.getSelectedItem().toString();
                showViewContent.setText("Calculate success");

                String enquireString = "DepositLoan: " + selectedDepositLoan + ", Duration: " + selectedDuration;
                String command = "enquire";

                // sendPost(String Period,String rate,String type, String command)
                InterestRateAsyncTask interestRateAsyncTask = new InterestRateAsyncTask();
                interestRateAsyncTask.setCallback(InterestRateCalculatorActivity.this);
                interestRateAsyncTask.execute(selectedDuration, "", selectedDepositLoan, command);
            }
        });

        //  Modify按钮的点击事件
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理查询按钮点击事件
                // 这里可以获取选中的 Spinner 内容，然后将内容发送给后端
                String selectedDepositLoan = spinnerDepositLoan.getSelectedItem().toString();
                String selectedDuration = spinnerDuration.getSelectedItem().toString();
                String modifiedValue = editTextContent.getText().toString();
                String command = "modify";

                // sendPost(String Period,String rate,String type, String command)
                InterestRateAsyncTask interestRateAsyncTask = new InterestRateAsyncTask();
                interestRateAsyncTask.setCallback(InterestRateCalculatorActivity.this);
                interestRateAsyncTask.execute(selectedDuration, modifiedValue, selectedDepositLoan, command);
            }
        });


    }

    @Override
    public void onInterestResult(String period, String rate) {
        double time = 1;
        if(period.equals("Three months")){
            time = 0.25;
        } else if (period.equals("Six months")) {
            time = 0.5;
        }else if (period.equals("One Year")){
            time = 1;
        }else if (period.equals("Two Years")) {
            time = 2;
        }else if (period.equals("Three Years")) {
            time = 3;
        }else if (period.equals("Four Years")) {
            time = 4;
        }else if (period.equals("Five Years")) {
            time = 5;
        }
        System.out.println(period); // Two Years
        String modifiedValue = writeInterest.getText().toString();
        System.out.println(time);   // 0.0
        System.out.println(modifiedValue);  // 100000


        String interestFinal = Double.parseDouble(modifiedValue) * Double.parseDouble(rate) * time * 0.01 + "";
        // 回调函数中把数据显示在文本框中
        textViewContent.setText("Period: " + period + "  &  Rate: " + rate +
                                    "\n" + "The interest is: " + interestFinal);
        textViewContent.setTextSize(20);
    }
}