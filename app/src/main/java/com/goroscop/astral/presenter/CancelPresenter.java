package com.goroscop.astral.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goroscop.astral.provider.ProviderModule;
import com.goroscop.astral.utils.RxUtils;
import com.goroscop.astral.view.ViewCancel;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class CancelPresenter extends MvpPresenter<ViewCancel> {
    private static final String TAG = CancelPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;


    public CancelPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    private void addBackgroundDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void cancelSubscription(String token) {
        getViewState().showProgress(true);
        addBackgroundDisposable(
                ProviderModule.getUserProvider().cancelSubscription(token)
                        .compose(RxUtils.ioToMainTransformerSingle())
                        .subscribe(success -> {
                                    getViewState().cancelSubscription(success);
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
