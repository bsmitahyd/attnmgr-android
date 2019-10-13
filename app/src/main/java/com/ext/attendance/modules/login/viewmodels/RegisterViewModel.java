package com.ext.attendance.modules.login.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.base.Data;
import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.login.view.RegisterNavigator;
import com.ext.attendance.networkcall.MyApiService;
import com.google.gson.JsonObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;


public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {
    private Disposable mDisposable = null;
    private MutableLiveData<GeneralResponse> generalResponseMutableLiveData;
    private MutableLiveData<JsonObject> jsonObjectMutableLiveData;

    public RegisterViewModel() {
        generalResponseMutableLiveData = new MutableLiveData<>();
        jsonObjectMutableLiveData = new MutableLiveData<>();
        loadRegisterData();
    }

    private void loadRegisterData() {
        generalResponseMutableLiveData.setValue(new GeneralResponse());
        jsonObjectMutableLiveData.setValue(new JsonObject());

    }

    private void clearViewModelData() {
        generalResponseMutableLiveData.setValue(null);
        jsonObjectMutableLiveData.setValue(null);
    }

    public MutableLiveData<GeneralResponse> getGeneralResponseMutableLiveData() {
        return generalResponseMutableLiveData;
    }

    public MutableLiveData<JsonObject> getJsonObjectMutableLiveData() {
        return jsonObjectMutableLiveData;
    }

    public boolean inputValidation(String Name, String lName, String email, String contact) {
        if (Name.isEmpty()) {
            return false;
        }
//        if(mName.isEmpty()){
//            return false;
//        }
        if (lName.isEmpty()) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        if (contact.isEmpty()) {
            return false;
        }
        if (contact.length() < 10) {
            return false;
        }

//        if(emergancyContact.isEmpty()){
//            return false;
//        }
//        if(emergancyContact.length()<10){
//            return false;
//        }
//        if(dob.isEmpty()){
//            return false;
//        }
//        if(doj.isEmpty()){
//            return false;
//        }
//        if(department.isEmpty()){
//            return false;
//        }
//        if(designation.isEmpty()){
//            return false;
//        }
        return true;
    }

    public void employeeRegister() {
        getNavigator().loadProgressBar(true);
        mDisposable = MyApiService.getService().employeeRegisterApiCall(jsonObjectMutableLiveData.getValue())
                .subscribe(new Consumer<GeneralResponse>() {
                    @Override
                    public void accept(GeneralResponse generalResponse) throws Exception {
                        getNavigator().loadProgressBar(false);
                        if (generalResponse.getStatus() == 200) {
                            mDisposable.dispose();
                            generalResponseMutableLiveData.setValue(generalResponse);
                        } else {
                            if (generalResponse.getMessage() != null)
                                Timber.d("RES_ERROR_MSG:%s", generalResponse.getMessage());

                            getNavigator().loadProgressBar(false);
                            mDisposable.dispose();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getNavigator().loadProgressBar(false);
                        getNavigator().handleError(throwable);
                    }
                });

    }
}
