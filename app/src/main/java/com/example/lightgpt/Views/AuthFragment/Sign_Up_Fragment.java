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
import android.widget.Toast;

import com.example.lightgpt.R;
import com.example.lightgpt.Repositories.AuthRepo;
import com.example.lightgpt.databinding.FragmentSignUpBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Sign_Up_Fragment extends Fragment {

    private FragmentSignUpBinding binding;
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_sign_Up_Fragment_to_sign_In_Fragment);
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tilUsername.setErrorEnabled(false);
                binding.tilPassword.setErrorEnabled(false);
                binding.tilConfirmPassword.setErrorEnabled(false);
                if(isValid()){
                    authViewModel.signUp(
                            binding.etUsername.getText().toString(),
                            binding.etPassword.getText().toString()
                    );
                    binding.btnContinue.setEnabled(false);
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
                binding.btnContinue.setEnabled(true);
                if (state == null) return;
                if (state == AuthRepo.USERNAME_DUPLICATE){
                    binding.tilUsername.setError("Username is already registered.");
                }
                else if(state == AuthRepo.ERROR_CONNECTION){
                    Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isValid(){
        boolean isValid = true;
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        String confirmPassword = binding.etConfirmPassword.getText().toString();

        if(username.isEmpty()){
            binding.tilUsername.setError("Username must not be empty");
            isValid = false;
        }

        if(password.isEmpty()){
            binding.tilPassword.setError("Password must not be empty");
            isValid = false;
        }


        if(confirmPassword.isEmpty()){
            binding.tilConfirmPassword.setError("Confirm password must not be empty");
            isValid = false;
        }
        else if(!password.equals(confirmPassword)){
            binding.tilConfirmPassword.setError("Password mismatch");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}