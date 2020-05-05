package com.goroscop.astral.provider;

import com.goroscop.astral.api.Backend;
import com.goroscop.astral.model.Horoscope;
import com.goroscop.astral.model.Pay;
import com.goroscop.astral.model.Token;
import com.goroscop.astral.model.User;
import com.goroscop.astral.network.NetworkModule;

import io.reactivex.Single;

public class PageProviderImpl {
    private Backend mBackendService;

    PageProviderImpl() {
        initNetworkModule();
    }

    private void initNetworkModule() {
        mBackendService = NetworkModule.getBackEndService();
    }

    public Single<Token> registration(String name,
                                      String birthday,
                                      String gender,
                                      String city,
                                      String email,
                                      String password) {
        return mBackendService.registration(name, birthday, gender, city, email, password);
    }

    public Single<Token> login(String email, String password) {
        return mBackendService.login(email, password);
    }

    public Single<User> getUser(String token) {
        return mBackendService.getUser(token);
    }

    public Single<Horoscope> getHoroscope(String token) {
        return mBackendService.getHoroscope(token);
    }

    public Single<String> setDevice(String token,
                                    String fcmToken,
                                    String deviceId,
                                    String typeDevice,
                                    Integer active) {
        return mBackendService.setDevice(token, fcmToken, deviceId, typeDevice, active);
    }

    public Single<Pay> getPayUrl(String token) {
        return mBackendService.getPayUrl(token);
    }


}
