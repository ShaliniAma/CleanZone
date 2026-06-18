package com.s23010457.cleanzone.ui.userprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.s23010457.cleanzone.R;
import com.s23010457.cleanzone.LoginActivity;
import com.s23010457.cleanzone.RegistrationActivity;
import com.s23010457.cleanzone.WelcomeActivity;

public class UserProfileFragment extends Fragment {


    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Populate user details from SharedPreferences
        android.widget.TextView nameTv = view.findViewById(R.id.profileName);
        android.widget.TextView emailTv = view.findViewById(R.id.profileEmail);
        android.widget.TextView mobileTv = view.findViewById(R.id.profileMobile);

        if (getActivity() != null) {
            android.content.SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE);
            String firstName = prefs.getString("first_name", "Shalini");
            String lastName = prefs.getString("last_name", "Amanda");
            String email = prefs.getString("email", "shalini@cleanzone.com");
            String mobile = prefs.getString("mobile_number", "+1 234 567 890");

            nameTv.setText(firstName + " " + lastName);
            emailTv.setText(email);
            mobileTv.setText(mobile);
        }

        Button logoutBtn = view.findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(getActivity(), "Logged out successfully!", android.widget.Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        return view;
    }
}
