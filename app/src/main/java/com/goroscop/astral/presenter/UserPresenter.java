package com.goroscop.astral.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goroscop.astral.provider.ProviderModule;
import com.goroscop.astral.utils.RxUtils;
import com.goroscop.astral.view.ViewGetToken;
import com.goroscop.astral.view.ViewGetUser;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class UserPresenter extends MvpPresenter<ViewGetUser> {
    private static final String TAG = UserPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;


    public UserPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    private void addBackgroundDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void getUser(String token) {
        getViewState().showProgress(true);
        addBackgroundDisposable(
                ProviderModule.getUserProvider().getUser(token)
                        .compose(RxUtils.ioToMainTransformerSingle())
                        .subscribe(success -> {
                                    getViewState().getUser(success);
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
