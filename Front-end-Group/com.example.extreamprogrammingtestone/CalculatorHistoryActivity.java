    package com.example.extreamprogrammingtestone;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.graphics.Typeface;

    import androidx.appcompat.app.AppCompatActivity;

    import java.util.Arrays;
    import java.util.List;

    import HTTPUtilsPackage.CalculatorAsynGetHistory;

    public class CalculatorHistoryActivity extends AppCompatActivity implements CalculatorAsynGetHistory.OnCalculatorHistoryListener  {
        private LinearLayout llHistoryRecords;
        public String content;
        private Button refreshBtn;

        // get方法：
        public void setContent(String content){
            this.content = content;
        }

        // CalculatorHistoryActivity
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calculator_history);

            llHistoryRecords = findViewById(R.id.ll_history_records);

            refreshBtn = (Button)findViewById(R.id.btn_refresh);

            CalculatorAsynGetHistory calculatorAsynGetHistory = new CalculatorAsynGetHistory();
            calculatorAsynGetHistory.setCallback(this);
            calculatorAsynGetHistory.execute(PersonalinfoMessage.getPsw(), PersonalinfoMessage.getUser());


            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CalculatorAsynGetHistory calculatorAsynGetHistory = new CalculatorAsynGetHistory();
                    calculatorAsynGetHistory.setCallback(CalculatorHistoryActivity.this);
                    calculatorAsynGetHistory.execute(PersonalinfoMessage.getPsw(), PersonalinfoMessage.getUser());
                }
            });
        }

        private void show(List<String> historyList) {
            llHistoryRecords.removeAllViews(); // 清除之前的视图

            for (String equation : historyList) {
                Log.i("CalculatorAsyncGetHistory", equation);
                TextView historyItem = new TextView(this);
                historyItem.setText(equation);
                historyItem.setTextSize(30); // 设置字体大小为16sp，你可以根据需要调整大小
                // 设置文本颜色为黑色
                historyItem.setTextColor(getResources().getColor(android.R.color.black));
                // 设置字体为粗体
                historyItem.setTypeface(historyItem.getTypeface(), Typeface.BOLD);
                llHistoryRecords.addView(historyItem);
            }
        }

        @Override
        public void onCalculatorResult(String result) {
            // 接收到数据库的所有消息，记录为 result
            Log.d("CalculatorHistoryActivity", "Result: " + result);
            // 进行数据处理
            // 去掉方括号
            String content = result.substring(1, result.length() - 1);
            // 通过逗号分割字符串，得到每个等式
            String[] equations = content.split(", ");
            // 创建历史记录列表
            List<String> historyList = Arrays.asList(equations);

            for (String equation : historyList) {
                Log.d("CalculatorAsyncGetHistory", equation);
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
