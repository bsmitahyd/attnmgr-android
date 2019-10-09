package com.ext.attendance.modules.login.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.ext.attendance.modules.login.view.LoginNavigator;
import com.google.gson.JsonObject;

import io.reactivex.disposables.Disposable;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private Disposable mDisposable = null;

    private MutableLiveData<LoginResponseModel> loginResponseModelMutableLiveData;
    private MutableLiveData<JsonObject> loginRequestJsonObjectData;

    public LoginViewModel() {
        loginResponseModelMutableLiveData = new MutableLiveData<>();
        loginRequestJsonObjectData = new MutableLiveData<>();
        loadLoginData();
    }

    private void loadLoginData() {
        loginResponseModelMutableLiveData.setValue(null);
        loginRequestJsonObjectData.setValue(null);
    }

      public MutableLiveData<LoginResponseModel> getLoginResponseModelMutableLiveData() {
        return loginResponseModelMutableLiveData;
    }

    public MutableLiveData<JsonObject> getLoginRequestJsonObjectData() {
        return loginRequestJsonObjectData;
    }

    public boolean inputValidation(String empId, String password, String deviceId) {
        if (empId.isEmpty()) {
            return false;
        }
        if (empId.length() < 9) {
            return false;
        }
        if (password.isEmpty()) {
            return false;
        }
        if (password.length() < 6) {
            return false;
        }
        if(deviceId.isEmpty()){
            return false;
        }
        return true;
    }
   // public void loginEmployee(LoginResponseModel )
}
