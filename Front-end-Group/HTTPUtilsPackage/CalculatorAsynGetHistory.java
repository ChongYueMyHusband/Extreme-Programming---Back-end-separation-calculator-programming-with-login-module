package HTTPUtilsPackage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.extreamprogrammingtestone.CalculatorHistoryActivity;

public class CalculatorAsynGetHistory  extends AsyncTask<String, Void, String> {
    private OnCalculatorHistoryListener callback;

    // Interface to handle PersonalInfo result callback
    public interface OnCalculatorHistoryListener {
        void onCalculatorResult(String res);
    }


    // Set the callback for handling registration result
    public void setCallback(OnCalculatorHistoryListener  callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        // 在后台线程中执行网络请求
        Log.i("CalculatorAsyncGetHistory", Arrays.toString(params)); // [fan123123, 2387171466]
        return CalculatorHistoryHTTPUtil.sendPost(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            // 接收到数据库的所有消息，记录为result
            Log.d("CalculatorAsyncGetHistory", "Result: " + result);
            // 接收到的值：[ 2 * 4 5 =90.0,  8 * 6 + 6 =54.0,  9 * 9 =81.0,  3 * 6 =18.0,  2 / 3 =0.666667,  2 / 3 =0.666667]


            // 通过回调将结果传递给 CalculatorHistoryActivity
            if (callback != null) {
                Log.d("CalculatorAsyncGetHistory", "传递成功！进入callback");
                callback.onCalculatorResult(result);
            }


        } catch (Exception e) {
            e.printStackTrace();
            // Handle JSON parsing error, if needed
        }
    }

    // 解析 JSON 字符串 -- 未使用
    private List<String> parseJsonResult(String jsonResult) {
        List<String> historyList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonResult);

            for (int i = 0; i < jsonArray.length(); i++) {
                String historyItem = jsonArray.getString(i);
                historyList.add(historyItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
        }

        return historyList;
    }
}
