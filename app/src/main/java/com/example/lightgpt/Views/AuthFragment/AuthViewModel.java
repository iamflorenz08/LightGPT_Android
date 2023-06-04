package com.example.lightgpt.Views.AuthFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lightgpt.Models.TokenModel;
import com.example.lightgpt.Models.UserModel;
import com.example.lightgpt.Repositories.AuthRepo;
import com.example.lightgpt.Repositories.ChatBotRepo;
import com.example.lightgpt.Repositories.ProfileRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private AuthRepo authRepo;
    private ChatBotRepo chatBotRepo;
    private ProfileRepo profileRepo;
    private MutableLiveData<Integer> mAuthState;
    private MutableLiveData<TokenModel> mAccessToken;

    @Inject
    public AuthViewModel(AuthRepo authRepo, ChatBotRepo chatBotRepo, ProfileRepo profileRepo){
        this.authRepo = authRepo;
        this.chatBotRepo = chatBotRepo;
        this.profileRepo = profileRepo;
        this.mAuthState = authRepo.getmAuthState();
        this.mAccessToken = authRepo.getmAccessToken();

        //default
        mAuthState.setValue(null);
    }

    public void signIn(
            String username,
            String password
    ){
        authRepo.signIn(new UserModel(username,password));
    }

    public void signUp(
            String username,
            String password
    ){
        authRepo.signUp(new UserModel(username, password));
    }

    public void signOut(){
        chatBotRepo.clearData();
        profileRepo.clearData();
        authRepo.signOut();
    }

    public LiveData<Integer> getmAuthState() {
        return mAuthState;
    }

    public LiveData<TokenModel> getmAccessToken() {
        return mAccessToken;
    }
}