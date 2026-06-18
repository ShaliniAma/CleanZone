package com.s23010457.cleanzone.ui.contactus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.s23010457.cleanzone.R;

public class ContactUsFragment extends Fragment {

    private EditText etName, etMessage;
    private Button btnSend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        etName = view.findViewById(R.id.et_contact_name);
        etMessage = view.findViewById(R.id.et_contact_message);
        btnSend = view.findViewById(R.id.btn_send_contact);

        btnSend.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                etName.setError("Name is required");
                etName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(message)) {
                etMessage.setError("Message is required");
                etMessage.requestFocus();
                return;
            }

            // Mock submission toast
            Toast.makeText(requireContext(), "Message Sent! We'll get back to you soon.", Toast.LENGTH_LONG).show();

            // Clear inputs
            etName.setText("");
            etMessage.setText("");
        });

        return view;
    }
}
