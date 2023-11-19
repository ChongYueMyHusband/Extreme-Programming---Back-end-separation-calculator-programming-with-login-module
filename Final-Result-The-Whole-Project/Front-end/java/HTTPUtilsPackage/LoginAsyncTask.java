package HTTPUtilsPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAsyncTask extends AsyncTask<String, Void, String>  {

    private OnLoginResultListener callback;
    private SharedPreferences sp;
    private Context context; // Add this field

    // Constructor to receive the context
    public LoginAsyncTask(Context context) {
        this.context = context;
    }

    // Interface to handle login result callback
    public interface OnLoginResultListener {
        void onLoginResult(boolean isSuccess);
    }

    // Set the callback for handling login result
    public void setCallback(OnLoginResultListener callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        // 在后台线程中执行网络请求
        return LoginHTTPUtil.sendPost(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the result of the network request
        // Parse the JSON result to determine success
        try {
            Log.d("LoginAsyncTask", "LoginResult: " + result);
            JSONObject jsonResult = new JSONObject(result);
            boolean isSuccess = jsonResult.getBoolean("success") || jsonResult.getString("success").equalsIgnoreCase("true");

            // Notify the callback with the login result
            if (callback != null) {
                callback.onLoginResult(isSuccess);
            }

            // If login is successful, trigger page transition and save user info to SharedPreferences
            if (isSuccess) {
                // Extract user information from the JSON response
                JSONObject userData = jsonResult.getJSONObject("data");
                String userId = userData.getString("id");
                String userName = userData.optString("userName", ""); // Replace with the actual field name
                String userAge = userData.optString("age", ""); // Replace with the actual field name
                String userSex = userData.getString("sex");
                String userIdiograph = userData.getString("idiograph");
                // Add other fields as needed

                // Save user information to SharedPreferences
                saveUserInfoToSharedPreferences(userId, userName, userAge, userIdiograph, userSex);

            }



        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error, if needed
        }
    }

    // Method to save user information to SharedPreferences
    private void saveUserInfoToSharedPreferences(String userId, String userName, String userAge, String userIdiograph, String userSex ) {
        // 存储个人信息
        // SharedPreferences 是一个用于存储简单键值对数据的 Android API。这些数据可以持久保存，以便在应用程序关闭后仍然可用。
        //  创建了一个名为 "user_info" 的 SharedPreferences 文件，并指定了访问模式为 Context.MODE_PRIVATE，表示只有你的应用可以访问这个数据。
        // Context.MODE_PRIVATE，表示只有你的应用可以访问这个数据。
        // SharedPreferences 的数据是与特定的 Context 相关的。不同的 Context 对象会创建不同的 SharedPreferences 数据文件，
        // 这意味着如果你在一个活动中使用了一个 Context 对象来存储数据，然后在另一个活动中使用不同的 Context 对象来访问相同的数据文件，
        // 它们将无法访问相同的数据。
        // 此处的context就是主活动的，构造器传递了主活动的context
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        // 对象 editor 用于编辑和存储数据。你可以使用 editor 来添加、修改或删除数据，然后将更改应用到 SharedPreferences 文件。
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 将用户的用户名信息存储在 SharedPreferences 中。这行代码将 "username" 作为键，receivedUsername 作为值存储。
        editor.putString("userName", userName);
        editor.putString("idiograph", userIdiograph);
        editor.putString("age", userAge);
        editor.putString("sex", userSex);
        // Add other fields as needed
        editor.apply();
    }
}
