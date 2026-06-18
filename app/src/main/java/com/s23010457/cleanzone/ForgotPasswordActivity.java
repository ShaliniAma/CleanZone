package com.s23010457.cleanzone;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.s23010457.cleanzone.databinding.ForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.submitbtn.setOnClickListener(v -> {
            String email = binding.editTextTextEmailAddress.getText().toString().trim();
            String phone = binding.editTextPhone.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                binding.editTextPhone.setError("Mobile number is required");
                binding.editTextPhone.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(email)) {
                binding.editTextTextEmailAddress.setError("Email address is required");
                binding.editTextTextEmailAddress.requestFocus();
                return;
            }

            if (!email.contains("@")) {
                binding.editTextTextEmailAddress.setError("Please enter a valid email");
                binding.editTextTextEmailAddress.requestFocus();
                return;
            }

            // Mock reset success
            Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent to " + email, Toast.LENGTH_LONG).show();
            finish();
        });
    }
}
