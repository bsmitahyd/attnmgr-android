package com.ext.attendance.modules.login.viewmodels;

public interface LoginNavigator {
    void handleError(Throwable throwable);

    void login();

    void loadProgressBar(boolean showProgress);
}
