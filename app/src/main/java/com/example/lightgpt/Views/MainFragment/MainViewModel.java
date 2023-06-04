package com.example.lightgpt.Views.MainFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.UserModel;
import com.example.lightgpt.Repositories.AuthRepo;
import com.example.lightgpt.Repositories.ChatBotRepo;
import com.example.lightgpt.Repositories.ProfileRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private AuthRepo authRepo;
    private ChatBotRepo chatBotRepo;
    private ProfileRepo profileRepo;
    private String accessToken;
    private MutableLiveData<ArrayList<ConversationModel>> mConversations;
    private MutableLiveData<UserModel> mUserDetails;

    @Inject
    public MainViewModel(AuthRepo authRepo, ChatBotRepo chatBotRepo, ProfileRepo profileRepo){
        this.authRepo = authRepo;
        this.chatBotRepo = chatBotRepo;
        this.profileRepo = profileRepo;
        this.accessToken = authRepo.getmAccessToken().getValue().getAccess_token();
        this.mConversations = chatBotRepo.getmConversations();
        this.mUserDetails = profileRepo.getmUserDetails();
    }

    public void loadConversations(){
        chatBotRepo.loadConversations("Bearer " + accessToken);
    }

    public void loadUserDetails(){
        profileRepo.loadUserDetails("Bearer " + accessToken);
    }

    public void deleteConversation(String conversation_id){
        String access_token = authRepo.getmAccessToken().getValue().getAccess_token();
        chatBotRepo.deleteConversation("Bearer " + access_token, conversation_id);
    }

    public LiveData<ArrayList<ConversationModel>> getmConversations() {
        return mConversations;
    }

    public LiveData<UserModel> getmUserDetails() {
        return mUserDetails;
    }
}