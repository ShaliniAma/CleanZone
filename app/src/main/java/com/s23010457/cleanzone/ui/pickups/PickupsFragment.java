package com.s23010457.cleanzone.ui.pickups;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.s23010457.cleanzone.R;

public class PickupsFragment extends Fragment {


    public static PickupsFragment newInstance() {
        return new PickupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickups, container, false);


//        Button actionButton = view.findViewById(R.id.activity_btn);
//        actionButton.setOnClickListener(v -> {
//            BottomNavigationView navView = requireActivity().findViewById(R.id.bottom_nav);
//            navView.setSelectedItemId(R.id.nav_dashboard);
//        });

        return view;
    }
}
