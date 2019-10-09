package com.ext.attendance.modules.login.view;

public interface RegisterNavigator {
    void handleError(Throwable throwable);

    void register();

    void loadProgressBar(boolean showProgress);
}
