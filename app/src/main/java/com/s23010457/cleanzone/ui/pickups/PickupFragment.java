package com.s23010457.cleanzone.ui.pickups;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.s23010457.cleanzone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PickupFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private RecyclerView rvPickups;
    private PickupAdapter adapter;
    private List<PickupLocation> locations = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();

    public static PickupFragment newInstance() {
        return new PickupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup, container, false);

        // Initialize MapView
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Load locations from JSON
        locations = loadLocations();

        // Set up RecyclerView
        rvPickups = view.findViewById(R.id.rv_pickups);
        rvPickups.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PickupAdapter(locations, (location, position) -> {
            // When a list item is tapped, move map camera to that location
            adapter.setSelectedPosition(position);
            if (googleMap != null) {
                LatLng target = new LatLng(location.getLat(), location.getLng());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(target, 15f));

                // Show the marker info window
                if (position < markers.size()) {
                    markers.get(position).showInfoWindow();
                }
            }
        });
        rvPickups.setAdapter(adapter);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (locations.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < locations.size(); i++) {
            PickupLocation loc = locations.get(i);
            LatLng latLng = new LatLng(loc.getLat(), loc.getLng());

            // Choose marker color based on waste type
            float markerColor;
            switch (loc.getType()) {
                case "Recyclables":
                    markerColor = BitmapDescriptorFactory.HUE_AZURE;
                    break;
                case "Organic Waste":
                    markerColor = BitmapDescriptorFactory.HUE_ORANGE;
                    break;
                case "E-Waste":
                    markerColor = BitmapDescriptorFactory.HUE_VIOLET;
                    break;
                default:
                    markerColor = BitmapDescriptorFactory.HUE_GREEN;
                    break;
            }

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(loc.getName())
                    .snippet(loc.getSchedule())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));

            if (marker != null) {
                marker.setTag(i);
                markers.add(marker);
            }

            boundsBuilder.include(latLng);
        }

        // Move camera to fit all markers
        try {
            LatLngBounds bounds = boundsBuilder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            // Fallback: center on first location
            LatLng first = new LatLng(locations.get(0).getLat(), locations.get(0).getLng());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 13f));
        }

        // When a marker is clicked, highlight the corresponding list item
        googleMap.setOnMarkerClickListener(marker -> {
            Object tag = marker.getTag();
            if (tag instanceof Integer) {
                int position = (Integer) tag;
                adapter.setSelectedPosition(position);
                rvPickups.smoothScrollToPosition(position);
            }
            return false; // let default behavior (info window) happen
        });
    }

    private List<PickupLocation> loadLocations() {
        List<PickupLocation> list = new ArrayList<>();
        try {
            InputStream is = requireContext().getAssets().open("pickup_locations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                list.add(new PickupLocation(
                        obj.getString("name"),
                        obj.getDouble("lat"),
                        obj.getDouble("lng"),
                        obj.getString("address"),
                        obj.getString("schedule"),
                        obj.getString("type")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== MapView lifecycle callbacks =====

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) mapView.onSaveInstanceState(outState);
    }
}