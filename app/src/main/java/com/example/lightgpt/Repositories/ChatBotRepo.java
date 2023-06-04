package com.example.lightgpt.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.MessageModel;
import com.example.lightgpt.Networks.IChatBotService;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatBotRepo {
    private IChatBotService iChatBotService;
    private MutableLiveData<ArrayList<ConversationModel>> mConversations;
    private MutableLiveData<ArrayList<MessageModel>> mMessages;
    private MutableLiveData<String> mConversationId;
    private MutableLiveData<Boolean> isLoading;
    private Call<ConversationModel> cMessages;
    public ChatBotRepo(IChatBotService iChatBotService){
        this.iChatBotService = iChatBotService;
        this.mConversations = new MutableLiveData<>();
        this.mMessages = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>(false);
        this.mConversationId = new MutableLiveData<>();
    }

    public void deleteConversation(String access_token, String conversation_id){
        iChatBotService.deleteConversation(access_token, conversation_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    for(ConversationModel conversation : mConversations.getValue()){
                        if(conversation.get_id().equals(conversation_id)){
                            mConversations.getValue().remove(conversation);
                            mConversations.postValue(mConversations.getValue());
                            return;
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void loadConversations(String access_token){
        iChatBotService.loadConversation(access_token).enqueue(new Callback<ArrayList<ConversationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ConversationModel>> call, Response<ArrayList<ConversationModel>> response) {
                if(response.isSuccessful()){
                    mConversations.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConversationModel>> call, Throwable t) {

            }
        });
    }

    public void loadMessages(String access_token, String conversation_id){
        iChatBotService.loadMessages(access_token, conversation_id).enqueue(new Callback<ArrayList<MessageModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageModel>> call, Response<ArrayList<MessageModel>> response) {
                if(response.isSuccessful()){
                    mMessages.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MessageModel>> call, Throwable t) {

            }
        });
    }

    public void sendMessage(String access_token){
        isLoading.setValue(true);
        ConversationModel conversation = new ConversationModel(
                mConversationId.getValue(),
                null,
                mMessages.getValue()
        );
        cMessages = iChatBotService.chatBot(access_token, conversation);
        cMessages.enqueue(new Callback<ConversationModel>() {
            @Override
            public void onResponse(Call<ConversationModel> call, Response<ConversationModel> response) {
                if(response.isSuccessful()){
                    ArrayList<MessageModel> messages = new ArrayList<>(mMessages.getValue());
                    messages.add(response.body().getMessages().get(0));
                    mMessages.postValue(messages);

                    if(mConversationId.getValue() == null){
                        String id = response.body().get_id();
                        String title = response.body().getTitle();
                        ArrayList<ConversationModel> conversations = new ArrayList<>(mConversations.getValue());
                        conversations.add(0,new ConversationModel(id,title, null));
                        mConversations.postValue(conversations);
                        mConversationId.setValue(id);
                    }

                }
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<ConversationModel> call, Throwable t) {
                isLoading.setValue(false);
            }
        });

    }

    public void cancelMessage(){
        try {
            cMessages.cancel();
        }catch (NullPointerException e){

        }
    }

    public void clearData(){
        mConversations.setValue(null);
        mMessages.setValue(null);
    }

    public MutableLiveData<ArrayList<ConversationModel>> getmConversations() {
        return mConversations;
    }

    public MutableLiveData<ArrayList<MessageModel>> getmMessages() {
        return mMessages;
    }

    public MutableLiveData<String> getmConversationId() {
        return mConversationId;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
