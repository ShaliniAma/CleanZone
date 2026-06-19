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

/**
 * MainActivity is the main screen of the application.
 * It manages the side navigation drawer, the bottom navigation bar,
 * and uses an accelerometer to detect if the phone is flipped face down to lock the app.
 */
public class MainActivity extends AppCompatActivity {

    // App bar configuration to manage back button and top level screens
    private AppBarConfiguration mAppBarConfiguration;
    // View binding is used to easily access view hierarchies
    private ActivityMainBinding binding;

    // Sensor instances to monitor screen orientation
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isFlipped = false;
    
    // Tracks when the app goes into background for screen auto-locking
    private long lastPausedTime = 0;
    // Skips auto-lock trigger once if returning from lock screen itself
    private boolean ignoreNextResumeLock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up toolbar/action bar
        setSupportActionBar(binding.appBarMain.toolbar);
        
        // Floating action button setup with standard snackbar notification popup
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
        
        // Set of layout IDs which should be treated as top-level navigation destinations.
        // Clicking menu items for these will display hamburger menu icon instead of back arrow.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_notifications, R.id.nav_dashboard, R.id.nav_pickups, R.id.nav_user_profile, 
                R.id.nav_activity_list, R.id.nav_settings, R.id.nav_complaints, R.id.nav_reports, R.id.nav_contact_us)
                .setOpenableLayout(drawer)
                .build();

        // Setup NavController for fragment switching navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Custom listener for sidebar navigation drawer to intercept logout button
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                performLogout();
                drawer.closeDrawers();
                return true;
            }
            // For other menu items, use normal fragment navigation
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            drawer.closeDrawers();
            return handled;
        });

        // Setup bottom navigation bar view compatibility logic
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Initialize accelerometer flip sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        // Decide whether to show screen lock when app returns from background
        if (ignoreNextResumeLock) {
            ignoreNextResumeLock = false;
            lastPausedTime = 0;
        } else {
            // Trigger lock if biometric lock preference is enabled and app was in background > 3 seconds
            android.content.SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLockEnabled = prefs.getBoolean("biometric_lock_enabled", true);
            if (isLockEnabled && lastPausedTime > 0 && (System.currentTimeMillis() - lastPausedTime) > 3000) {
                showLockScreen();
            }
        }
        
        // Register sensor listener when app starts playing
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Record time when app was sent to background
        lastPausedTime = System.currentTimeMillis();
        // Unregister sensor listener when app is paused to save battery life
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorListener);
        }
    }

    // Sensor listener configuration: checks if device is turned face down
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float z = event.values[2];
                // If Z-axis force is less than -8.0 m/s^2, the phone is facing flat down
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

    /**
     * Launches the biometric lock screen.
     */
    private void showLockScreen() {
        ignoreNextResumeLock = true;
        Intent intent = new Intent(this, BiometricLockActivity.class);
        startActivity(intent);
    }

    /**
     * Clears session storage details and returns to the Welcome startup screen.
     */
    private void performLogout() {
        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WelcomeActivity.class);
        // Clear task stack to prevent user pressing back key to return to logged-in screens
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate options menu items
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle action bar Up button navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}