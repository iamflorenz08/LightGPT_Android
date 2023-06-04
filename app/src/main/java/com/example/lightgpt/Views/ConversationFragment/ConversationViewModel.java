package com.example.lightgpt.Views.ConversationFragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.MessageModel;
import com.example.lightgpt.Repositories.AuthRepo;
import com.example.lightgpt.Repositories.ChatBotRepo;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ConversationViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private AuthRepo authRepo;
    private ChatBotRepo chatBotRepo;

    private String access_token;
    private MutableLiveData<ArrayList<MessageModel>> mMessages;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> mConversationId;
    @Inject
    public ConversationViewModel(AuthRepo authRepo, ChatBotRepo chatBotRepo){
        this.authRepo = authRepo;
        this.chatBotRepo = chatBotRepo;
        this.mMessages = chatBotRepo.getmMessages();
        this.mConversationId = chatBotRepo.getmConversationId();
        this.isLoading = chatBotRepo.getIsLoading();
        this.access_token = authRepo.getmAccessToken().getValue().getAccess_token();
        this.mMessages.setValue(null);
    }

    public void loadMessages(){
        chatBotRepo.loadMessages("Bearer " + access_token, mConversationId.getValue());
    }

    public void sendMessage(String message){
        ArrayList<MessageModel> messages;
        if(mMessages.getValue() == null){
            messages = new ArrayList<>();
        }
        else {
            messages = new ArrayList<>(mMessages.getValue());
        }
        messages.add(new MessageModel("user", message));
        mMessages.setValue(messages);
        chatBotRepo.sendMessage("Bearer " + access_token);
    }

    public void cancelMessage(){
        chatBotRepo.cancelMessage();
    }

    public void setConversationId(String conversation_id){
        mConversationId.setValue(conversation_id);
    }

    public LiveData<ArrayList<MessageModel>> getmMessages() {
        return mMessages;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}