package com.example.lightgpt.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.lightgpt.Models.TokenModel;
import com.example.lightgpt.Models.UserModel;
import com.example.lightgpt.Networks.IAuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepo {
    public static final Integer INVALID_CREDENTIAL = 100;
    public static final Integer ERROR_CONNECTION = 101;
    public static final Integer USERNAME_DUPLICATE = 102;
    private IAuthService iAuthService;
    private MutableLiveData<TokenModel> mAccessToken;
    private MutableLiveData<Integer> mAuthState;
    public AuthRepo(IAuthService iAuthService){
        this.iAuthService = iAuthService;
        this.mAccessToken = new MutableLiveData<>();
        this.mAuthState = new MutableLiveData<>();
    }

    public void signIn(UserModel user){
        iAuthService.signIn(user).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()){
                    mAccessToken.setValue(response.body());
                    mAuthState.setValue(null);
                }
                else{
                    mAuthState.setValue(INVALID_CREDENTIAL);
                }
            }
            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                mAuthState.setValue(ERROR_CONNECTION);
            }
        });
    }

    public void signUp(UserModel user){
        iAuthService.signUp(user).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if(response.isSuccessful()){
                    mAccessToken.setValue(response.body());
                    mAuthState.setValue(null);
                }
                else{
                    mAuthState.setValue(USERNAME_DUPLICATE);
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                mAuthState.setValue(ERROR_CONNECTION);
            }
        });
    }

    public void signOut(){
        mAccessToken.setValue(null);
        mAuthState.setValue(null);
    }

    public MutableLiveData<Integer> getmAuthState() {
        return mAuthState;
    }

    public MutableLiveData<TokenModel> getmAccessToken() {
        return mAccessToken;
    }
}
