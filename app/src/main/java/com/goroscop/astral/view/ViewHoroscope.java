package com.goroscop.astral.view;

import com.arellomobile.mvp.MvpView;
import com.goroscop.astral.model.Horoscope;

public interface ViewHoroscope extends MvpView {
    void getHoroscope(Horoscope horoscope);

    void showProgress(boolean isLoading);

    void showError(String error);
}
