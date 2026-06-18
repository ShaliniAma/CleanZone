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

public class BiometricLockActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_lock);

        setupBiometricPrompt();

        Button btnUnlock = findViewById(R.id.btn_unlock);
        btnUnlock.setOnClickListener(v -> authenticate());

        // Auto-trigger on launch
        authenticate();
    }

    private void setupBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        // User cancelled or too many attempts
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
                        Toast.makeText(BiometricLockActivity.this,
                                "Unlocked!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(BiometricLockActivity.this,
                                "Authentication failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Allow biometrics + device credential (PIN/pattern) as fallback
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("CleanZone Locked")
                .setSubtitle("Authenticate to continue")
                .setAllowedAuthenticators(
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
                                | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();
    }

    private void authenticate() {
        // Check if biometrics or device credential is available
        BiometricManager biometricManager = BiometricManager.from(this);
        int canAuthenticate = biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_WEAK
                        | BiometricManager.Authenticators.DEVICE_CREDENTIAL);

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo);
        } else {
            // Device has no biometrics or screen lock — just unlock
            Toast.makeText(this, "No biometrics available. Unlocking...", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Pressing back on lock screen exits the app entirely (no bypass)
        finishAffinity();
    }
}
