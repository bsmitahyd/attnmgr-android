package com.ext.attendance.base;

public interface CommonNavigator {
    void handleError(Throwable throwable);

    void loadProgressBar(boolean showProgress);
}
