package com.s23010457.cleanzone.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.s23010457.cleanzone.R;

/**
 * SettingsFragment handles toggles for app settings:
 * 1. Dark theme mode
 * 2. Push notification preferences
 * 3. Biometric screen auto-lock on pause
 * It persists state choices using SharedPreferences.
 */
public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout for settings screen
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Access shared local storage preference file 'UserPrefs'
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Bind switches from XML layouts
        SwitchCompat switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        SwitchCompat switchNotifications = view.findViewById(R.id.switch_notifications);
        SwitchCompat switchBiometricLock = view.findViewById(R.id.switch_biometric_lock);

        // Read saved setting values from storage (assign defaults if not configured)
        boolean isDarkMode = prefs.getBoolean("dark_mode_enabled", false);
        boolean isNotificationsEnabled = prefs.getBoolean("notifications_enabled", true);
        boolean isBiometricLockEnabled = prefs.getBoolean("biometric_lock_enabled", true);

        // Configure Dark Mode Switch listener
        if (switchDarkMode != null) {
            // Apply current saved toggle checked state
            switchDarkMode.setChecked(isDarkMode);
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Save new setting value
                prefs.edit().putBoolean("dark_mode_enabled", isChecked).apply();
                
                // Toggle app rendering colors theme dynamically
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                Toast.makeText(getContext(), "Theme updated!", Toast.LENGTH_SHORT).show();
            });
        }

        // Configure Push Notifications Switch listener
        if (switchNotifications != null) {
            // Apply current saved toggle checked state
            switchNotifications.setChecked(isNotificationsEnabled);
            switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Save notifications enable preference state
                prefs.edit().putBoolean("notifications_enabled", isChecked).apply();
                Toast.makeText(getContext(), isChecked ? "Notifications enabled" : "Notifications muted", Toast.LENGTH_SHORT).show();
            });
        }

        // Configure Biometric Screen Auto-Lock Switch listener
        if (switchBiometricLock != null) {
            // Apply current saved toggle checked state
            switchBiometricLock.setChecked(isBiometricLockEnabled);
            switchBiometricLock.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Save lock screen enable preference state
                prefs.edit().putBoolean("biometric_lock_enabled", isChecked).apply();
                Toast.makeText(getContext(), isChecked ? "Auto-Lock enabled" : "Auto-Lock disabled", Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }
}
