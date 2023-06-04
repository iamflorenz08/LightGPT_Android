package com.example.lightgpt.Views.AuthFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightgpt.Repositories.AuthRepo;
import com.example.lightgpt.databinding.FragmentSignInBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Sign_In_Fragment extends Fragment {

    private FragmentSignInBinding binding;
    private AuthViewModel authViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tilUsername.setError(null);
                binding.tilPassword.setError(null);
                binding.tilUsername.setErrorEnabled(false);
                binding.tilPassword.setErrorEnabled(false);
                binding.tvErrorMessage.setVisibility(View.GONE);
                if (isValid()){
                    authViewModel.signIn(
                            binding.etUsername.getText().toString(),
                            binding.etPassword.getText().toString()
                    );
                    binding.btnSignIn.setEnabled(false);
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        authViewModel.getmAuthState().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                binding.btnSignIn.setEnabled(true);
                if (state == null) return;
                if(state == AuthRepo.INVALID_CREDENTIAL){
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                }
                else if(state == AuthRepo.ERROR_CONNECTION){

                }
            }
        });
    }

    private boolean isValid(){
        Boolean flag = true;
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();

        if(username.isEmpty()){
            binding.tilUsername.setError("Username is empty");
            flag = false;
        }

        if(password.isEmpty()){
            binding.tilPassword.setError("Password is empty");
            flag = false;
        }

        return flag;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}