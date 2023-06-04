package com.example.lightgpt.Views.ConversationFragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.MessageModel;
import com.example.lightgpt.R;
import com.example.lightgpt.Views.Adapters.MessagesAdapter;
import com.example.lightgpt.databinding.FragmentConversationBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ConversationFragment extends Fragment {

    public static final String CONVERSATION_ID = "CONVERSATION_ID";
    private ConversationViewModel mViewModel;
    private FragmentConversationBinding binding;
    private MessagesAdapter messagesAdapter;
    private LinearLayoutManager llmMessages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConversationViewModel.class);
        try {
           mViewModel.setConversationId(getArguments().getString(CONVERSATION_ID));
        }catch (NullPointerException e){
            mViewModel.setConversationId(null);
        }

        mViewModel.loadMessages();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentConversationBinding.inflate(inflater, container, false);
        messagesAdapter = new MessagesAdapter();
        llmMessages = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llmMessages.setStackFromEnd(true);
        binding.rvMessages.setAdapter(messagesAdapter);
        binding.rvMessages.setLayoutManager(llmMessages);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.etMessage.getText().toString();
                if(message.isEmpty()) return;
                mViewModel.sendMessage(message);
                binding.etMessage.setText(null);
            }
        });

        mViewModel.getmMessages().observe(getViewLifecycleOwner(), new Observer<ArrayList<MessageModel>>() {
            @Override
            public void onChanged(ArrayList<MessageModel> messages) {
                if(messages == null || messages.size() == 0) {
                    binding.llTips.setVisibility(View.VISIBLE);
                    return;
                };
                binding.llTips.setVisibility(View.GONE);
                messagesAdapter.setMessageList(messages);
                binding.rvMessages.scrollToPosition(messages.size() - 1);
            }
        });

        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading == null) return;
                if(isLoading){
                    binding.btnSend.setVisibility(View.GONE);
                    binding.lavLoading.setVisibility(View.VISIBLE);
                    return;
                }
                binding.btnSend.setVisibility(View.VISIBLE);
                binding.lavLoading.setVisibility(View.GONE);
            }
        });

        binding.btnExample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.sendMessage("Create a simple code using php");
            }
        });

        binding.btnExample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.sendMessage("What are your limitations? And Who developed you?");
            }
        });

        binding.btnExample3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.sendMessage("Who is Elon Musk?");
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mViewModel.cancelMessage();
    }
}