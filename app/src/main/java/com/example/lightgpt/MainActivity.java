package com.example.lightgpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.lightgpt.Models.TokenModel;
import com.example.lightgpt.Views.AuthFragment.AuthViewModel;
import com.example.lightgpt.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentContainerView.getId());
        navController = navHostFragment.getNavController();

        authViewModel.getmAccessToken().observe(this, new Observer<TokenModel>() {
            @Override
            public void onChanged(TokenModel tokenModel) {
                if (tokenModel != null){
                    navController.setGraph(R.navigation.main_navigation);
                    return;
                }
                navController.setGraph(R.navigation.auth_navigation);
            }
        });
    }

}