package com.goroscop.astral.provider;

import com.goroscop.astral.api.Backend;
import com.goroscop.astral.model.Token;
import com.goroscop.astral.network.NetworkModule;

import io.reactivex.Single;

public class PageProviderImpl {
    private Backend mBackendService;

    public PageProviderImpl() {
        initNetworkModule();
    }

    protected void initNetworkModule() {
        mBackendService = NetworkModule.getBackEndService();
    }

    public Single<Token> registration(String name,
                                      Long birthday,
                                      String gender,
                                      String city,
                                      String email,
                                      String password) {
        return mBackendService.registration(name, birthday, gender, city, email, password);
    }

    public Single<Token> login(String email,
                               String password) {
        return mBackendService.login(email, password);
    }


}
