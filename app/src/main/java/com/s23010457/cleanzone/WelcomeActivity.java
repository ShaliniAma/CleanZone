package com.s23010457.cleanzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.s23010457.cleanzone.databinding.ActivityWelcomeBinding;

/**
 * WelcomeActivity is the first screen that opens when the app starts.
 * It lets the user navigate to the login screen and shows options for social login.
 */
public class WelcomeActivity extends AppCompatActivity {

    // View binding is used to access the layout elements easily
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load the theme preference (light or dark mode) from local storage before building the screen
        android.content.SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode_enabled", false);
        if (isDarkMode) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        
        // Enable edge-to-edge layout so content can draw under status/navigation bars
        EdgeToEdge.enable(this);
        
        // Inflate layout using view binding
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Adjust padding of the view so it doesn't overlap with system status bars or notch
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Click listener: when the user clicks 'Login', go to the LoginActivity screen
        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Click listener for social login buttons (Facebook, Google, etc.)
        // Shows a message saying it's not implemented yet
        View.OnClickListener mockSocialClick = v -> {
            Toast.makeText(WelcomeActivity.this, "Social Login is not integrated yet.", Toast.LENGTH_SHORT).show();
        };

        // Bind mock listener to social login icon buttons
        binding.logoq.setOnClickListener(mockSocialClick);
        binding.logoqq.setOnClickListener(mockSocialClick);
        binding.logoqqj.setOnClickListener(mockSocialClick);
    }
}