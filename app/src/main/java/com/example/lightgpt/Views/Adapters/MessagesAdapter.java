package com.example.lightgpt.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightgpt.Models.MessageModel;
import com.example.lightgpt.databinding.LayoutBotChatBinding;
import com.example.lightgpt.databinding.LayoutUserChatBinding;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {
    private static final Integer USER = 100;
    private static final Integer BOT = 101;
    private ArrayList<MessageModel> messageList;

    public MessagesAdapter(){
        this.messageList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position).getRole().equals("user")){
            return USER;
        }
        return BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == USER){
            LayoutUserChatBinding binding = LayoutUserChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new UserViewHolder(binding);
        }

        LayoutBotChatBinding binding = LayoutBotChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BotViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        if(message.getRole().equals("user")){
            LayoutUserChatBinding binding = ((UserViewHolder)holder).binding;
            binding.tvMessage.setText(message.getContent().trim());
        }
        else if(message.getRole().equals("assistant")){
            LayoutBotChatBinding binding = ((BotViewHolder)holder).binding;
            binding.tvMessage.setText(message.getContent().trim());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setMessageList(ArrayList<MessageModel> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    private class UserViewHolder extends RecyclerView.ViewHolder{
        private LayoutUserChatBinding binding;
        public UserViewHolder(@NonNull LayoutUserChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class BotViewHolder extends RecyclerView.ViewHolder{
        private LayoutBotChatBinding binding;
        public BotViewHolder(@NonNull LayoutBotChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
