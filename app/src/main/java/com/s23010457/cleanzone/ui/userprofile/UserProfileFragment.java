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


        Button login = view.findViewById(R.id.login);
        Button register = view.findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regintent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(regintent);

            }
        });

        Button welcomebtn = view.findViewById(R.id.welcome);

        welcomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welintent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(welintent);
            }
        });

        return view;
    }
}
