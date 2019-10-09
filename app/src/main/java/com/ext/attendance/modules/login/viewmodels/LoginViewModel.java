package com.ext.attendance.modules.login.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.login.models.LoginResponseModel;

import io.reactivex.disposables.Disposable;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private Disposable mDisposable = null;

    private MutableLiveData<LoginResponseModel> loginResponseModelMutableLiveData;
    private MutableLiveData<GeneralResponse> generalResponseMutableLiveData;

    public LoginViewModel() {
        loginResponseModelMutableLiveData = new MutableLiveData<>();
        generalResponseMutableLiveData = new MutableLiveData<>();
        loaLoginData();
    }

    private void loaLoginData() {
        loginResponseModelMutableLiveData.setValue(null);
        generalResponseMutableLiveData.setValue(null);
    }

      public MutableLiveData<LoginResponseModel> getLoginResponseModelMutableLiveData() {
        return loginResponseModelMutableLiveData;
    }

    public MutableLiveData<GeneralResponse> getGeneralResponseMutableLiveData() {
        return generalResponseMutableLiveData;
    }

    public boolean inputValidation(String mobile, String password) {
        if (mobile.isEmpty()) {
            return false;
        }
        if (mobile.length() < 10) {
            return false;
        }
        if (password.isEmpty()) {
            return false;
        }
        if (password.length() < 4) {
            return false;
        }
        return true;
    }
   // public void loginEmployee(LoginResponseModel )
}
