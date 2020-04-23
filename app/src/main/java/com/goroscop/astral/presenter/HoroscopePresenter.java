package com.goroscop.astral.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goroscop.astral.provider.ProviderModule;
import com.goroscop.astral.utils.RxUtils;
import com.goroscop.astral.view.ViewGetUser;
import com.goroscop.astral.view.ViewHoroscope;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class HoroscopePresenter extends MvpPresenter<ViewHoroscope> {
    private static final String TAG = HoroscopePresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;


    public HoroscopePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    private void addBackgroundDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void getHoroscope(String token) {
        getViewState().showProgress(true);
        addBackgroundDisposable(
                ProviderModule.getUserProvider().getHoroscope(token)
                        .compose(RxUtils.ioToMainTransformerSingle())
                        .subscribe(success -> {
                                    getViewState().getHoroscope(success);
                                    getViewState().showProgress(false);
                                },
                                error -> {
                                    Log.d(TAG, Objects.requireNonNull(error.getMessage()));
                                    getViewState().showError(error.getMessage());
                                    getViewState().showProgress(false);
                                }
                        ));
    }

    @Override
    protected void finalize() throws Throwable {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        super.finalize();
    }
}
