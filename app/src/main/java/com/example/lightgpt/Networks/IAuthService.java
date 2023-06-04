package com.example.lightgpt.Networks;

import com.example.lightgpt.Models.TokenModel;
import com.example.lightgpt.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {
    @POST("/api/v1/auth/sign-up")
    Call<TokenModel> signUp(@Body UserModel user);
    @POST("/api/v1/auth/sign-in")
    Call<TokenModel> signIn(@Body UserModel user);
}
