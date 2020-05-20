package com.goroscop.astral.view;

import com.arellomobile.mvp.MvpView;

public interface ViewCancel extends MvpView {
    void cancelSubscription(String url);

    void showProgress(boolean isLoading);

    void showError(String error);
}
