package com.s23010457.cleanzone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

/**
 * BiometricLockActivity is the lock screen activity that appears
 * when the app is resumed after being in the background for more than 3 seconds.
 * It uses Android Biometric APIs to authenticate the user (using fingerprints, face, or PIN/pattern).
 */
public class BiometricLockActivity extends AppCompatActivity {

    // Prompt object that displays system auth dialog
    private BiometricPrompt biometricPrompt;
    // Configuration details for the prompt dialog
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_lock);

        // Configure the biometric prompt callbacks and setup dialog details
        setupBiometricPrompt();

        // Setup unlock manual trigger button
        Button btnUnlock = findViewById(R.id.btn_unlock);
        btnUnlock.setOnClickListener(v -> authenticate());

        // Automatically trigger biometric unlock request when the screen launches
        authenticate();
    }

    /**
     * Prepares the BiometricPrompt dialog structure and callback listeners.
     */
    private void setupBiometricPrompt() {
        // Main executor to run biometric callbacks on main UI thread
        Executor executor = ContextCompat.getMainExecutor(this);

        // Create the prompt framework with event listeners
        biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        // Triggered when user cancels prompt or biometric is not set up
                        if (errorCode == BiometricPrompt.ERROR_USER_CANCELED
                                || errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                            Toast.makeText(BiometricLockActivity.this,
                                    "Tap UNLOCK to try again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BiometricLockActivity.this,
                                    "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        // Successfully unlocked! Show toast and close lock screen
                        Toast.makeText(BiometricLockActivity.this,
                                "Unlocked!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        // Authentication failed (e.g. fingerprint not recognized)
                        Toast.makeText(BiometricLockActivity.this,
                                "Authentication failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Set up the appearance and security criteria for the system dialog.
        // It allows device credentials (PIN, pattern) as fallback.
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("CleanZone Locked")
                .setSubtitle("Authenticate to continue")
                .setAllowedAuthenticators(
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
                                | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();
    }

    /**
     * Checks if biometric hardware is ready and requests system authentication.
     */
    private void authenticate() {
        BiometricManager biometricManager = BiometricManager.from(this);
        // Verify compatibility checks
        int canAuthenticate = biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_WEAK
                        | BiometricManager.Authenticators.DEVICE_CREDENTIAL);

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            // Hardware exists and user configured screen lock; show dialog
            biometricPrompt.authenticate(promptInfo);
        } else {
            // Device has no screen lock or biometrics configured; just unlock
            Toast.makeText(this, "No biometrics available. Unlocking...", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Pressing back on lock screen exits the app entirely so the user cannot bypass it.
        finishAffinity();
    }
}
