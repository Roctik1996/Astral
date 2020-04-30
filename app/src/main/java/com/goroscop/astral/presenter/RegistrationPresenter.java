package com.goroscop.astral.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goroscop.astral.provider.ProviderModule;
import com.goroscop.astral.utils.RxUtils;
import com.goroscop.astral.view.ViewGetToken;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<ViewGetToken> {
    private static final String TAG = RegistrationPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;


    public RegistrationPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    private void addBackgroundDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void registration(String name,
                      String birthday,
                      String gender,
                      String city,
                      String email,
                      String password) {
        getViewState().showProgress(true);
        addBackgroundDisposable(
                ProviderModule.getUserProvider().registration(name, birthday, gender, city, email, password)
                        .compose(RxUtils.ioToMainTransformerSingle())
                        .subscribe(success -> {
                                    getViewState().getToken(success);
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
