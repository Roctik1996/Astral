package com.goroscop.astral.view;

import com.arellomobile.mvp.MvpView;
import com.goroscop.astral.model.Pay;

public interface ViewGetPayUrl extends MvpView {
    void getPayUrl(Pay url);

    void showProgress(boolean isLoading);

    void showError(String error);
}
