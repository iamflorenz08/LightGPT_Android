package com.example.lightgpt.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.lightgpt.Models.UserModel;
import com.example.lightgpt.Networks.IProfileService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepo {
    private IProfileService iProfileService;
    private MutableLiveData<UserModel> mUserDetails;
    public ProfileRepo(IProfileService iProfileService){
        this.iProfileService = iProfileService;
        this.mUserDetails = new MutableLiveData<>();
    }

    public void loadUserDetails(String access_token){
        iProfileService.loadUserDetails(access_token).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    mUserDetails.setValue(response.body());
                }
                Log.d("my_dev", "onResponse: 1" + response.message());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("my_dev", "onResponse: 2" + t.getMessage());
            }
        });
    }

    public void clearData(){
        mUserDetails.setValue(null);
    }

    public MutableLiveData<UserModel> getmUserDetails() {
        return mUserDetails;
    }
}
