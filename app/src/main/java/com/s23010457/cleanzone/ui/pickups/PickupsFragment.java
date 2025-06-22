package com.s23010457.cleanzone.ui.pickups;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.s23010457.cleanzone.R;

public class PickupsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap minimap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickups, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.minimap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        minimap = googleMap;

        // Default marker at OUSL
        LatLng ousl = new LatLng(6.883158130680555, 79.88662939870927);
        minimap.addMarker(new MarkerOptions().position(ousl).title("The Open University"));
        minimap.moveCamera(CameraUpdateFactory.newLatLngZoom(ousl, 18));
        minimap.getUiSettings().setZoomControlsEnabled(true);
    }
}
