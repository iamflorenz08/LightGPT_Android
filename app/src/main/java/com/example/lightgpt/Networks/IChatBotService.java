package com.example.lightgpt.Networks;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.MessageModel;
import com.example.lightgpt.Models.TokenModel;
import com.example.lightgpt.Models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IChatBotService {
    @POST("/api/v1/bot/chat")
    Call<ConversationModel> chatBot(@Header("authorization")String token, @Body ConversationModel conversationModel);
    @GET("/api/v1/bot/conversations")
    Call<ArrayList<ConversationModel>> loadConversation(@Header("authorization")String token);
    @DELETE("/api/v1/bot/conversations")
    Call<Void> deleteConversation(@Header("authorization")String token, @Query("id")String conversation_id);
    @GET("/api/v1/bot/messages")
    Call<ArrayList<MessageModel>> loadMessages(@Header("authorization")String token, @Query("id")String conversation_id);
}
