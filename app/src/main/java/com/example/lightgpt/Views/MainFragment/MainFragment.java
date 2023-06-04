package com.example.lightgpt.Views.MainFragment;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightgpt.Models.ConversationModel;
import com.example.lightgpt.Models.UserModel;
import com.example.lightgpt.R;
import com.example.lightgpt.Views.Adapters.ConversationAdapter;
import com.example.lightgpt.Views.AuthFragment.AuthViewModel;
import com.example.lightgpt.Views.ConversationFragment.ConversationFragment;
import com.example.lightgpt.databinding.FragmentMainBinding;
import com.example.lightgpt.databinding.LayoutBottomSheetDeleteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment implements ConversationAdapter.ConversationAdapterListener {

    private MainViewModel mViewModel;
    private AuthViewModel authViewModel;
    private FragmentMainBinding binding;
    private ConversationAdapter conversationAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.loadConversations();
        mViewModel.loadUserDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container,false);
        conversationAdapter = new ConversationAdapter(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sidebar.bringToFront();
        binding.rvConversations.setAdapter(conversationAdapter);
        binding.rvConversations.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.layout.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.signOut();
            }
        });

        binding.btnAddConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_conversationFragment);
            }
        });

        mViewModel.getmConversations().observe(getViewLifecycleOwner(), new Observer<ArrayList<ConversationModel>>() {
            @Override
            public void onChanged(ArrayList<ConversationModel> conversationModels) {
                if(conversationModels == null) return;
                binding.tvConversationStatus.setVisibility(View.GONE);
                if(conversationModels.size() == 0){
                    binding.tvConversationStatus.setVisibility(View.VISIBLE);
                }

                conversationAdapter.setConversationList(conversationModels);
            }
        });

        mViewModel.getmUserDetails().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel == null) return;
                binding.layout.tvUsername.setText(userModel.getUsername());
            }
        });

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),onBackPressedCallback);
    }

    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
            else {
                getActivity().finish();
            }
        }
    };


    @Override
    public void ItemOnClick(String _id) {
        Bundle bundle = new Bundle();
        bundle.putString(ConversationFragment.CONVERSATION_ID,_id);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_conversationFragment, bundle);
    }

    @Override
    public void ItemOnLongClick(String conversation_id) {
        LayoutBottomSheetDeleteBinding binding = LayoutBottomSheetDeleteBinding.inflate(getLayoutInflater());
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(binding.getRoot());

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteConversation(conversation_id);
                dialog.hide();
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}