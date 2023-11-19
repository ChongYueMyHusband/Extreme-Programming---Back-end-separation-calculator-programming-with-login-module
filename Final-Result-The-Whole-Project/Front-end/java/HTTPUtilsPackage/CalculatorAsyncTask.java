package HTTPUtilsPackage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class CalculatorAsyncTask extends AsyncTask<String, Void, String> {
    private OnCalculatorResultListener callback;
    private Context context; // Add this field
    // Interface to handle PersonalInfo result callback
    public interface OnCalculatorResultListener {
        void onCalculatorResult(List<String> res);
    }


    // Constructor to receive the context
    public CalculatorAsyncTask(Context context) {
        this.context = context;
    }
    // Set the callback for handling registration result
    public void setCallback(OnCalculatorResultListener callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        // 在后台线程中执行网络请求
        Log.i("CalculatorAsyncTask", Arrays.toString(params));
        return CalculatorHTTPUtil.sendPost(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        // 不需要处理返回值
    }
}