package com.s23010457.cleanzone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.s23010457.cleanzone.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up sign up button click listener
        binding.btnSignUp.setOnClickListener(v -> {
            String email = binding.email.getText().toString().trim();
            String firstName = binding.firstName.getText().toString().trim();
            String lastName = binding.lastName.getText().toString().trim();
            String mobileNumber = binding.mobileNumber.getText().toString().trim();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                binding.email.setError("Email is required");
                binding.email.requestFocus();
                return;
            }

            if (!email.contains("@")) {
                binding.email.setError("Please enter a valid email");
                binding.email.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(firstName)) {
                binding.firstName.setError("First name is required");
                binding.firstName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(lastName)) {
                binding.lastName.setError("Last name is required");
                binding.lastName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(mobileNumber)) {
                binding.mobileNumber.setError("Mobile number is required");
                binding.mobileNumber.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                binding.password.setError("Password is required");
                binding.password.requestFocus();
                return;
            }

            if (password.length() < 6) {
                binding.password.setError("Password must be at least 6 characters");
                binding.password.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {
                binding.confirmPassword.setError("Passwords do not match");
                binding.confirmPassword.requestFocus();
                return;
            }

            if (!binding.cbTerms.isChecked()) {
                Toast.makeText(RegistrationActivity.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save registration details to SharedPreferences
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("first_name", firstName);
            editor.putString("last_name", lastName);
            editor.putString("mobile_number", mobileNumber);
            editor.putString("password", password);
            editor.apply();

            // Mock registration success
            Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            // Finish and return to LoginActivity
            finish();
        });

        // Redirect back to LoginActivity
        binding.alreadylogIntxt.setOnClickListener(v -> {
            finish();
        });
    }
}