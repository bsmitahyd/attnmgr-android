package com.ext.attendance.modules.home.view;

public interface CurrentMonthAttendanceNavigator {
    void handleError(Throwable throwable);

    void getEmployeeAttendanceList();

    void loadProgressBar(boolean showProgress);
}
