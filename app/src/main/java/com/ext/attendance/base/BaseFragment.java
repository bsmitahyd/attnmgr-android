package com.ext.attendance.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.ResponseBody;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class BaseFragment extends Fragment {

    @LayoutRes
    protected abstract int layoutRes();


    public String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            //{"success":false,"err":{"statusCode":500}}
            if (jsonObject.has("err")) {
                JSONObject jsonObjectErr = jsonObject.optJSONObject("err");
                if (jsonObjectErr.optInt("statusCode") == 500)
                    return "Internal server Error";
                else {
                    return jsonObjectErr.getString("message");
                }
            }

            if (jsonObject.has("error")) {
                JSONObject jsonObjectErr = jsonObject.optJSONObject("error");
                if (jsonObjectErr.optInt("statusCode") == 500)
                    return "Internal server Error";
                else {
                    return jsonObjectErr.getString("name");
                }
            }

            if (jsonObject.has("result")) {
                JSONObject jsonObjectErr = jsonObject.optJSONObject("result");
                return jsonObjectErr.getString("msg");
            }

            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static void hideKeyBoard(Activity context) {
        if (context != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            View currentFocus = context.getCurrentFocus();
            if (currentFocus != null) {
                inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}