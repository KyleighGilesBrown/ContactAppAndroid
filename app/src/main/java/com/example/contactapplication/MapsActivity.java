package com.example.contactapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.SupportMapFragment;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactapplication.databinding.ActivityContactMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
//    LocationManager locationManager;
//    LocationListener gpsListener;
//    LocationListener networkListener;
    final int PERMISSION_REQUEST_LOCATION = 101;
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
   // private ActivityContactMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityContactMapBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_contact_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapID);
        mapFragment.getMapAsync(this);
        createLocationRequest();
        createLocationCallback();
      //  initGetLocationButton();

        contactListButton();
        settingsButton();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void initGetLocationButton() {
        Button locationButton = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale
                                    (MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                                Snackbar.make(findViewById(R.id.mapID), "MyContactList requires this permission to locate " +
                                        "your contacts", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(
                                                MapsActivity.this, new String[]{
                                                        android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                                    }
                                }).show();
                            } else {
                                ActivityCompat.requestPermissions(MapsActivity.this, new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                            }
                        } else {
                            startLocationUpdates();
                        }
                    }}

                       catch(Exception e){
                        Toast.makeText(getBaseContext(), "Error requesting permission", Toast.LENGTH_LONG).show();
                    }
                }
    });
        }


    private void contactListButton() {
        ImageButton clButton = findViewById(R.id.listButtonID5);
        clButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ContactListActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void settingsButton() {
        ImageButton sButton = findViewById(R.id.settingButtonID5);
        sButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SettingsActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startLocationUpdates() {
        if ( Build.VERSION.SDK_INT >= 23 &&
        ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
                return;
        }
        try {
            locationManager = (LocationManager)getBaseContext().getSystemService(Context.LOCATION_SERVICE);
            gpsListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                    TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                    TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                    txtLatitude.setText(String.valueOf(location.getLatitude()));
                    txtLongitude.setText(String.valueOf(location.getLongitude()));
                    txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                }
                public void onStatusChanged(String provider, int status, Bundle extras) {};
                public void onProviderEnabled(String provider) {};
                public void onProviderDisabled(String provider) {};

            };
            networkListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                    TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                    TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                    txtLatitude.setText(String.valueOf(location.getLatitude()));
                    txtLongitude.setText(String.valueOf(location.getLongitude()));
                    txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                }
                public void onStatusChanged(String provider, int status, Bundle extras) {};
                public void onProviderEnabled(String provider) {};
                public void onProviderDisabled(String provider) {};

            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,networkListener);


        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //why not in textbook super call
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();

                }
                else {
                    Toast.makeText(MapsActivity.this,"MyContactList will not locate your contacts.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

}
private void createLocationCallback() {
        @Override

}
}
