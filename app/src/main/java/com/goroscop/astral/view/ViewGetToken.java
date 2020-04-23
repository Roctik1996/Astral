package com.goroscop.astral.view;

import com.arellomobile.mvp.MvpView;
import com.goroscop.astral.model.Token;

public interface ViewGetToken extends MvpView {
    void getToken(Token token);

    void showProgress(boolean isLoading);

    void showError(String error);
}
