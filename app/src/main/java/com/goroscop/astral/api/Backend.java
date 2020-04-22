package com.goroscop.astral.api;

import io.reactivex.Single;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface Backend {

    @Multipart
    @POST("/api/registration")
    Single<String> registration();

    @Multipart
    @POST("/api/login")
    Single<String> login();

    @Multipart
    @POST("/api/user")
    Single<String> getUser();

}
