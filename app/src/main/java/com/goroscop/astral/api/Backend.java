package com.goroscop.astral.api;

import com.goroscop.astral.model.Horoscope;
import com.goroscop.astral.model.Token;
import com.goroscop.astral.model.User;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Backend {

    @Multipart
    @POST("/api/registration")
    Single<Token> registration(@Part("name") String name,
                               @Part("birthday") Long birthday,
                               @Part("gender") String gender,
                               @Part("city") String city,
                               @Part("email") String email,
                               @Part("password") String password
                               );

    @Multipart
    @POST("/api/login")
    Single<Token> login(@Part("email") String email,
                        @Part("password") String password);

    @GET("/api/user")
    Single<User> getUser(@Header("Authorization") String token);

    @GET("/api/horoscope")
    Single<Horoscope> getHoroscope(@Header("Authorization") String token);



}
