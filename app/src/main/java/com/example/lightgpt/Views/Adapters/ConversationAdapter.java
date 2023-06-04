package com.example.lightgpt.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.databinding.LayoutConversationBinding;

import java.util.ArrayList;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {
    private ArrayList<ConversationModel> conversationList;
    private ConversationAdapterListener conversationAdapterListener;

    public ConversationAdapter(ConversationAdapterListener conversationAdapterListener){
        this.conversationList = new ArrayList<>();
        this.conversationAdapterListener = conversationAdapterListener;
    }
    @NonNull
    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutConversationBinding binding = LayoutConversationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationAdapter.ViewHolder holder, int position) {
        ConversationModel conversation = conversationList.get(position);
        LayoutConversationBinding binding = holder.binding;
        binding.tvTitle.setText(conversation.getTitle());
        binding.mvConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationAdapterListener.ItemOnClick(conversation.get_id());
            }
        });

        binding.mvConversation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                conversationAdapterListener.ItemOnLongClick(conversation.get_id());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public void setConversationList(ArrayList<ConversationModel> conversationList) {
        this.conversationList = conversationList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutConversationBinding binding;
        public ViewHolder(@NonNull LayoutConversationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ConversationAdapterListener{
        void ItemOnClick(String _id);
        void ItemOnLongClick(String conversation_id);
    }
}
