package HTTPUtilsPackage;



import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class InterestRateAsyncTask extends AsyncTask<String, Void, String> {
    private OnInterestListener callback;


    // Interface to handle PersonalInfo result callback
    public interface OnInterestListener {
        void onInterestResult(String period, String rate);
    }


    // Set the callback for handling registration result
    public void setCallback(OnInterestListener callback) {
        this.callback = callback;
    }


    @Override
    protected String doInBackground(String... params) {
        // 在后台线程中执行网络请求
        Log.i("InterestRateAsyncTask", Arrays.toString(params));
        return InterestRateHttoUtil.sendPost(params[0],params[1],params[2], params[3]);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            // 测试结果：
            // Result: {"message":"update successfully","success":true,"data":{"id":0,"rate":"","period":"Current Deposit"}}
            Log.d("InterestRateAsyncTask", "Result: " + result);
            JSONObject jsonResult = new JSONObject(result);
            String rate = "null";
            String period = "null";

            // 检查是否存在 "data" 字段
            if (jsonResult.has("data")) {
                // 获取 "data" 对象
                JSONObject dataObject = jsonResult.getJSONObject("data");

                // 检查是否存在 "rate" 字段
                if (dataObject.has("rate")) {
                    rate = dataObject.getString("rate");
                    // 处理 rate
                }

                // 检查是否存在 "period" 字段
                if (dataObject.has("period")) {
                    period = dataObject.getString("period");
                    // 处理 period
                }
            }


            // 通过回调将结果传递给 CalculatorHistoryActivity
            if (callback != null) {
                Log.d("InterestRateAsyncTask", "传递成功！进入callback");
                callback.onInterestResult(period, rate);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Handle JSON parsing error, if needed
        }
    }
}