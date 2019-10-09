package com.ext.attendance.base;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<N> extends ViewModel {

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;


    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}
