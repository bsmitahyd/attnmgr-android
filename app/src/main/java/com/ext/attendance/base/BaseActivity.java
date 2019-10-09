package com.ext.attendance.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.ext.attendance.R;

import org.json.JSONObject;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;


public abstract class BaseActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        ButterKnife.bind(this);
    }

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


    public Toolbar showToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {

                if (title.length() > 0) {
                    getSupportActionBar().setTitle("");
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);
                    toolbarTitle.setText(title);

                } else {
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setElevation(0);
            }


        }

        return toolbar;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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


}
