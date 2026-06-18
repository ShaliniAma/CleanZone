package com.s23010457.cleanzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.s23010457.cleanzone.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up login button click listener
        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString();

            if (TextUtils.isEmpty(username)) {
                binding.etUsername.setError("Username/Email cannot be empty");
                binding.etUsername.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                binding.etPassword.setError("Password cannot be empty");
                binding.etPassword.requestFocus();
                return;
            }

            // Retrieve registration details from SharedPreferences to check credentials
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String registeredEmail = prefs.getString("email", null);
            String registeredPassword = prefs.getString("password", null);

            if (registeredEmail != null && registeredPassword != null) {
                if (!username.equals(registeredEmail)) {
                    binding.etUsername.setError("Invalid Email/Username");
                    binding.etUsername.requestFocus();
                    return;
                }
                if (!password.equals(registeredPassword)) {
                    binding.etPassword.setError("Incorrect Password");
                    binding.etPassword.requestFocus();
                    return;
                }
            } else {
                // Save these as default if login directly (no registration data)
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", username);
                editor.putString("first_name", "Shalini");
                editor.putString("last_name", "Amanda");
                editor.putString("mobile_number", "+1 234 567 890");
                editor.apply();
            }

            // Login successful
            Toast.makeText(LoginActivity.this, "Welcome to Clean Zone!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            // Clear the activity task stack so user cannot back navigate into the login/welcome screens
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Navigate to RegistrationActivity
        binding.tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        // Navigate to ForgotPasswordActivity
        binding.tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}