package com.goroscop.astral.view;

import com.arellomobile.mvp.MvpView;
import com.goroscop.astral.model.User;

public interface ViewGetUser extends MvpView {
    void getUser(User user);

    void showProgress(boolean isLoading);

    void showError(String error);
}
