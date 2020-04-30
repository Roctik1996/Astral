package com.goroscop.astral.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goroscop.astral.provider.ProviderModule;
import com.goroscop.astral.utils.RxUtils;
import com.goroscop.astral.view.ViewSetDevice;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class DevicePresenter extends MvpPresenter<ViewSetDevice> {
    private static final String TAG = DevicePresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;


    public DevicePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    private void addBackgroundDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void setDevice(String token,
                          String fcmToken,
                          String deviceId) {
        addBackgroundDisposable(
                ProviderModule.getUserProvider().setDevice(token, fcmToken, deviceId, "android", 1)
                        .compose(RxUtils.ioToMainTransformerSingle())
                        .subscribe(success -> {
                                    getViewState().setDevice(success);
                                },
                                error -> {
                                    Log.d(TAG, Objects.requireNonNull(error.getMessage()));
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
