package com.ext.attendance.modules.login.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.ext.attendance.modules.login.view.LoginNavigator;
import com.ext.attendance.networkcall.MyApiService;
import com.google.gson.JsonObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private Disposable mDisposable = null;

    private MutableLiveData<LoginResponseModel> loginResponseModelMutableLiveData;
    private MutableLiveData<JsonObject> jsonObjectMutableLiveData;

    public LoginViewModel() {
        loginResponseModelMutableLiveData = new MutableLiveData<>();
        jsonObjectMutableLiveData = new MutableLiveData<>();
        loadLoginData();
    }

    private void loadLoginData() {
        loginResponseModelMutableLiveData.setValue(new LoginResponseModel());
        jsonObjectMutableLiveData.setValue(new JsonObject());
    }
    private void clearViewModelData() {
        loginResponseModelMutableLiveData.setValue(null);
        jsonObjectMutableLiveData.setValue(null);
    }


    public MutableLiveData<LoginResponseModel> getLoginResponseModelMutableLiveData() {
        return loginResponseModelMutableLiveData;
    }

    public MutableLiveData<JsonObject> jsonObjectMutableLiveData() {
        return jsonObjectMutableLiveData;
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
        if (password.length() < 5) {
            return false;
        }
        if (deviceId.isEmpty()) {
            return false;
        }
        return true;
    }

    public void loginEmployee() {
        getNavigator().loadProgressBar(true);
        mDisposable = MyApiService.getService().employeeLoginApiCall(jsonObjectMutableLiveData.getValue()).
                subscribe(new Consumer<LoginResponseModel>() {
            @Override
            public void accept(LoginResponseModel loginResponseModel) throws Exception {
                getNavigator().loadProgressBar(false);
                if (loginResponseModel.getStatus() == 200) {
                    mDisposable.dispose();
                    loginResponseModelMutableLiveData.setValue(loginResponseModel);
                } else {
                    getNavigator().loadProgressBar(false);
                    mDisposable.dispose();
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getNavigator().loadProgressBar(true);
                getNavigator().handleError(throwable);
            }

        });
    }
}
