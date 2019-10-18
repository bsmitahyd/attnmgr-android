package com.ext.attendance.modules.sidemenu.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.base.CommonNavigator;
import com.ext.attendance.modules.sidemenu.models.ChangePwdRequestModel;
import com.ext.attendance.modules.sidemenu.models.ChangePwdResponseModel;
import com.ext.attendance.networkcall.MyApiService;
import com.google.gson.JsonObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ChangePasswordViewModel extends BaseViewModel<CommonNavigator> {

    private Disposable mDisposable = null;
    private MutableLiveData<ChangePwdResponseModel> changePwdResponseModelMutableLiveData;
    private MutableLiveData<JsonObject> jsonObjectMutableLiveData;

    public ChangePasswordViewModel() {
        jsonObjectMutableLiveData = new MutableLiveData<>();
        changePwdResponseModelMutableLiveData = new MutableLiveData<>();
        loadChangeData();
    }

    private void loadChangeData() {
        changePwdResponseModelMutableLiveData.setValue(new ChangePwdResponseModel());
        jsonObjectMutableLiveData.setValue(new JsonObject());
    }

    public void clearViewModelData() {
        jsonObjectMutableLiveData.setValue(null);
        changePwdResponseModelMutableLiveData.setValue(null);
    }

    public MutableLiveData<JsonObject> getJsonObjectMutableLiveData() {
        return jsonObjectMutableLiveData;
    }

    public MutableLiveData<ChangePwdResponseModel> getChangePwdResponseModelMutableLiveData() {
        return changePwdResponseModelMutableLiveData;
    }

    public void changePassword() {
        getNavigator().loadProgressBar(true);
        mDisposable = MyApiService.getService().changePasswordApiCall(jsonObjectMutableLiveData.getValue()).
                subscribe(new Consumer<ChangePwdResponseModel>() {
                    @Override
                    public void accept(ChangePwdResponseModel changePwdResponseModel) throws Exception {
                        getNavigator().loadProgressBar(false);
                        if (changePwdResponseModel.getStatus() == 200) {
                            changePwdResponseModelMutableLiveData.setValue(changePwdResponseModel);
                            mDisposable.dispose();
                        } else {
                            getNavigator().loadProgressBar(false);
                            mDisposable.dispose();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
