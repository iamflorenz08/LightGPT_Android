package com.example.lightgpt.Networks;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IProfileService {
    @GET("/api/v1/user/details")
    Call<UserModel> loadUserDetails(@Header("authorization")String token);
}
