package com.s23010457.cleanzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.s23010457.cleanzone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isFlipped = false;
    private long lastPausedTime = 0;
    private boolean ignoreNextResumeLock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_notifications, R.id.nav_dashboard, R.id.nav_pickups, R.id.nav_user_profile, R.id.nav_activity_list, R.id.nav_settings, R.id.nav_complaints, R.id.nav_reports, R.id.nav_contact_us)
                .setOpenableLayout(drawer)
                .build();

        // Setup NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Intercept logout navigation in navigation drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                performLogout();
                drawer.closeDrawers();
                return true;
            }
            // For other items, use standard NavigationUI behavior
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            drawer.closeDrawers();
            return handled;
        });

        // Bottom Navigation setup using NavigationUI for proper handling
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Initialize Sensor Manager and Accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ignoreNextResumeLock) {
            ignoreNextResumeLock = false;
            lastPausedTime = 0;
        } else {
            // Trigger lock if background threshold exceeded (3 seconds)
            if (lastPausedTime > 0 && (System.currentTimeMillis() - lastPausedTime) > 3000) {
                showLockScreen();
            }
        }
        // Register sensor listener
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastPausedTime = System.currentTimeMillis();
        // Unregister sensor listener to save battery
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorListener);
        }
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float z = event.values[2];
                // z around -9.8 m/s^2 means screen is facing flat down
                if (z < -8.0) {
                    if (!isFlipped) {
                        isFlipped = true;
                        showLockScreen();
                    }
                } else {
                    isFlipped = false;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Not used
        }
    };

    private void showLockScreen() {
        ignoreNextResumeLock = true;
        Intent intent = new Intent(this, BiometricLockActivity.class);
        startActivity(intent);
    }

    private void performLogout() {
        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}