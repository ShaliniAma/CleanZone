package com.s23010457.cleanzone.ui.complaints;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class ComplaintsFragment extends Fragment {

    private EditText etTitle, etCategory, etDescription, etLocation;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        etTitle = view.findViewById(R.id.et_complaint_title);
        etCategory = view.findViewById(R.id.et_complaint_category);
        etDescription = view.findViewById(R.id.et_complaint_description);
        etLocation = view.findViewById(R.id.et_complaint_location);
        btnSubmit = view.findViewById(R.id.btn_submit_complaint);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                etTitle.setError("Title is required");
                etTitle.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(category)) {
                etCategory.setError("Category is required");
                etCategory.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(description)) {
                etDescription.setError("Description is required");
                etDescription.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(location)) {
                etLocation.setError("Location is required");
                etLocation.requestFocus();
                return;
            }

            // Save complaint to SharedPreferences
            saveComplaint(title, category, description, location);

            Toast.makeText(requireContext(), "Complaint Submitted Successfully!", Toast.LENGTH_LONG).show();

            // Clear inputs
            etTitle.setText("");
            etCategory.setText("");
            etDescription.setText("");
            etLocation.setText("");
        });

        return view;
    }

    private void saveComplaint(String title, String category, String description, String location) {
        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String existing = prefs.getString("submitted_complaints", "[]");

        try {
            JSONArray arr = new JSONArray(existing);
            JSONObject obj = new JSONObject();
            obj.put("title", title);
            obj.put("category", category);
            obj.put("description", description);
            obj.put("location", location);
            arr.put(obj);

            prefs.edit().putString("submitted_complaints", arr.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
