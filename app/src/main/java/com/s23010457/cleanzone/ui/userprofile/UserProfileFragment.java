package com.s23010457.cleanzone.ui.userprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.s23010457.cleanzone.R;
import com.s23010457.cleanzone.WelcomeActivity;
import com.s23010457.cleanzone.ui.complaints.Complaint;
import com.s23010457.cleanzone.ui.complaints.ComplaintAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Populate user details from SharedPreferences
        TextView nameTv = view.findViewById(R.id.profileName);
        TextView emailTv = view.findViewById(R.id.profileEmail);
        TextView mobileTv = view.findViewById(R.id.profileMobile);

        if (getActivity() != null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String firstName = prefs.getString("first_name", "Shalini");
            String lastName = prefs.getString("last_name", "Amanda");
            String email = prefs.getString("email", "shalini@cleanzone.com");
            String mobile = prefs.getString("mobile_number", "+1 234 567 890");

            nameTv.setText(firstName + " " + lastName);
            emailTv.setText(email);
            mobileTv.setText(mobile);
        }

        // Set up complaints RecyclerView
        RecyclerView rvComplaints = view.findViewById(R.id.rv_my_complaints);
        TextView tvNoComplaints = view.findViewById(R.id.tv_no_complaints);
        rvComplaints.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Complaint> complaints = loadComplaints();
        if (complaints.isEmpty()) {
            tvNoComplaints.setVisibility(View.VISIBLE);
            rvComplaints.setVisibility(View.GONE);
        } else {
            tvNoComplaints.setVisibility(View.GONE);
            rvComplaints.setVisibility(View.VISIBLE);
            ComplaintAdapter adapter = new ComplaintAdapter(complaints);
            rvComplaints.setAdapter(adapter);
        }

        // Logout button
        Button logoutBtn = view.findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }

    private List<Complaint> loadComplaints() {
        List<Complaint> list = new ArrayList<>();
        if (getActivity() == null) return list;

        SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String json = prefs.getString("submitted_complaints", "[]");

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                list.add(new Complaint(
                        obj.getString("title"),
                        obj.getString("category"),
                        obj.getString("description"),
                        obj.getString("location")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
