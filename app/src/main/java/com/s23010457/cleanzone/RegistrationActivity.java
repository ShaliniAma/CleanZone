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

/**
 * RegistrationActivity lets new users register a new profile.
 * It validates inputs (like matching passwords and ticking terms checkbox)
 * and saves user details into SharedPreferences.
 */
public class RegistrationActivity extends AppCompatActivity {

    // View binding is used to easily access input boxes and checkbox on the screen
    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Make layouts fit screen edge-to-edge
        EdgeToEdge.enable(this);
        
        // Inflate view binding layout
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Add window padding to avoid system overlay issues
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up what happens when the user clicks 'Sign Up' button
        binding.btnSignUp.setOnClickListener(v -> {
            // Get inputs entered by the user
            String email = binding.email.getText().toString().trim();
            String firstName = binding.firstName.getText().toString().trim();
            String lastName = binding.lastName.getText().toString().trim();
            String mobileNumber = binding.mobileNumber.getText().toString().trim();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();

            // Validate: email cannot be empty
            if (TextUtils.isEmpty(email)) {
                binding.email.setError("Email is required");
                binding.email.requestFocus();
                return;
            }

            // Validate: email must contain '@' symbol
            if (!email.contains("@")) {
                binding.email.setError("Please enter a valid email");
                binding.email.requestFocus();
                return;
            }

            // Validate: first name cannot be empty
            if (TextUtils.isEmpty(firstName)) {
                binding.firstName.setError("First name is required");
                binding.firstName.requestFocus();
                return;
            }

            // Validate: last name cannot be empty
            if (TextUtils.isEmpty(lastName)) {
                binding.lastName.setError("Last name is required");
                binding.lastName.requestFocus();
                return;
            }

            // Validate: mobile number cannot be empty
            if (TextUtils.isEmpty(mobileNumber)) {
                binding.mobileNumber.setError("Mobile number is required");
                binding.mobileNumber.requestFocus();
                return;
            }

            // Validate: password cannot be empty
            if (TextUtils.isEmpty(password)) {
                binding.password.setError("Password is required");
                binding.password.requestFocus();
                return;
            }

            // Validate: password length must be at least 6 characters
            if (password.length() < 6) {
                binding.password.setError("Password must be at least 6 characters");
                binding.password.requestFocus();
                return;
            }

            // Validate: password and confirm password must match exactly
            if (!password.equals(confirmPassword)) {
                binding.confirmPassword.setError("Passwords do not match");
                binding.confirmPassword.requestFocus();
                return;
            }

            // Validate: user must check the 'Terms and Conditions' checkbox
            if (!binding.cbTerms.isChecked()) {
                Toast.makeText(RegistrationActivity.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user details to local storage (SharedPreferences) under key 'UserPrefs'
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("first_name", firstName);
            editor.putString("last_name", lastName);
            editor.putString("mobile_number", mobileNumber);
            editor.putString("password", password);
            editor.apply();

            // Show success message
            Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            // Close this activity and go back to Login screen
            finish();
        });

        // Click listener: return to the Login screen if they click 'Already have an account? Login'
        binding.alreadylogIntxt.setOnClickListener(v -> {
            finish();
        });
    }
}