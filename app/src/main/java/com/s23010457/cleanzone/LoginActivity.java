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

/**
 * LoginActivity handles signing in to the app.
 * It checks input details and matches them against saved registration data.
 */
public class LoginActivity extends AppCompatActivity {

    // View binding is used to easily access text boxes and buttons on the screen
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Make layout fit the full device screen size
        EdgeToEdge.enable(this);
        
        // Inflate view binding layout
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Apply window padding so content does not overlap with screen notches/camera cutouts
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up what happens when the user clicks the 'Login' button
        binding.btnLogin.setOnClickListener(v -> {
            // Get username/email and password entered by the user
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString();

            // Validate: check if username text box is empty
            if (TextUtils.isEmpty(username)) {
                binding.etUsername.setError("Username/Email cannot be empty");
                binding.etUsername.requestFocus();
                return;
            }

            // Validate: check if password text box is empty
            if (TextUtils.isEmpty(password)) {
                binding.etPassword.setError("Password cannot be empty");
                binding.etPassword.requestFocus();
                return;
            }

            // Retrieve registered credentials saved in local storage (SharedPreferences)
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String registeredEmail = prefs.getString("email", null);
            String registeredPassword = prefs.getString("password", null);

            // If a user has previously registered, match the inputs against the saved data
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
                // If there's no registration details yet (first launch testing),
                // create mock profile data so they can login directly
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", username);
                editor.putString("first_name", "Shalini");
                editor.putString("last_name", "Amanda");
                editor.putString("mobile_number", "+1 234 567 890");
                editor.apply();
            }

            // Login is successful! Show success message
            Toast.makeText(LoginActivity.this, "Welcome to Clean Zone!", Toast.LENGTH_SHORT).show();

            // Navigate to the MainActivity screen
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            // Clear the activity task stack so user cannot back-navigate into the login/welcome screens
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Click listener: go to the Sign Up screen if they click 'Sign Up' link
        binding.tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        // Click listener: go to Forgot Password recovery screen if they click 'Forgot Password' link
        binding.tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}