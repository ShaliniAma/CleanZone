package com.s23010457.cleanzone.ui.dashboard;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s23010457.cleanzone.R;

public class DashboardFragment extends Fragment {

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Schedule -> Pickups fragment
        view.findViewById(R.id.dash_schedule).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_pickups);
        });

        // Complaints -> Complaints fragment
        view.findViewById(R.id.dash_complaints).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_complaints);
        });

        // Videos -> Activities fragment (educational content)
        view.findViewById(R.id.dash_videos).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_activity_list);
        });

        // Settings -> Settings fragment
        view.findViewById(R.id.dash_settings).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_settings);
        });

        // Reports -> Reports fragment
        view.findViewById(R.id.dash_reports).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_reports);
        });

        // Guide -> Activities fragment (learning content)
        view.findViewById(R.id.dash_guide).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_activity_list);
        });

        // Profile -> User Profile fragment
        view.findViewById(R.id.dash_profile).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_user_profile);
        });

        // Feedbacks -> Contact Us fragment
        view.findViewById(R.id.dash_feedbacks).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_contact_us);
        });

        return view;
    }
}

