package com.example.contactapplication;



import com.google.android.gms.location.LocationRequest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.SupportMapFragment;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap gMap;

    final int PERMISSION_REQUEST_LOCATION = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    ArrayList<Contact> contacts = new ArrayList<>();
    Contact currentContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_map);
        Bundle extras = getIntent().getExtras();
        try{
            ContactDataSource ds = new ContactDataSource(MapsActivity.this);
            ds.open();
            if(extras != null) {
                currentContact = ds.getSpecificContact(extras.getInt("contactid"));
            }
            else {
                contacts = ds.getContacts("editTextFirstID", "ASC");
            }
            ds.close();
        }
        catch(Exception e) {
            Toast.makeText(this,"Contact(s) could not be retrieved.", Toast.LENGTH_LONG).show();
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapID);
        mapFragment.getMapAsync(this);
        createLocationRequest();
        createLocationCallback();
        mapTypeButtons();
        contactListButton();
        settingsButton();
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

//
    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
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
        locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if( locationResult == null) {
                return;

            }
            for (Location location : locationResult.getLocations()) {
                Toast.makeText(getBaseContext(), "Lat: " + location.getLatitude() + "Long: " + location.getLongitude()
                        + "Accuracy: " + location.getAccuracy(), Toast.LENGTH_LONG).show(); }

            };
    };
}
private void startLocationUpdates() {
   if ( Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
        return;
}
   fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
   //mMap?
   gMap.setMyLocationEnabled(true);
    }
    private void stopLocationUpdates() {
        if(Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
    }
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int measuredWidth = size.x;
            int measuredHeight = size.y;

            if (contacts.size() > 0) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                boolean hasPoints = false;

                for (int i = 0; i < contacts.size(); i++) {
                    currentContact = contacts.get(i);
                    Geocoder geo = new Geocoder(this);
                    List<Address> addresses = null;
                    String address = currentContact.getEditTextStrAddress() + ", " +
                            currentContact.getEditTextCityAddress() + ", " +
                            currentContact.getEditTextStateAddress() + " " +
                            currentContact.getEditTextZipAddress() + ", " +
                            currentContact.getEditTextCountryAddress();

                    try {
                        addresses = geo.getFromLocationName(address, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addresses == null || addresses.isEmpty()) {
                        Toast.makeText(this, "Could not geocode: " + currentContact.getEditTextFirstID(), Toast.LENGTH_SHORT).show();
                        continue;
                    }

                    LatLng point = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    builder.include(point);
                    hasPoints = true;
                    gMap.addMarker(new MarkerOptions().position(point)
                            .title(currentContact.getEditTextFirstID())
                            .snippet(address));
                }

                if (hasPoints) {
                    gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), measuredWidth, measuredHeight, 450));
                }

            } else if (currentContact != null) {
                Geocoder geo = new Geocoder(this);
                List<Address> addresses = null;
                String address = currentContact.getEditTextStrAddress() + ", " +
                        currentContact.getEditTextCityAddress() + ", " +
                        currentContact.getEditTextStateAddress() + " " +
                        currentContact.getEditTextZipAddress() + ", " +
                        currentContact.getEditTextCountryAddress();

                try {
                    addresses = geo.getFromLocationName(address, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses == null || addresses.isEmpty()) {
                    Toast.makeText(this, "Could not geocode: " + currentContact.getEditTextFirstID(), Toast.LENGTH_SHORT).show();
                } else {
                    LatLng point = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    gMap.addMarker(new MarkerOptions().position(point)
                            .title(currentContact.getEditTextFirstID())
                            .snippet(address));
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
                }

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                alertDialog.setTitle("No Data");
                alertDialog.setMessage("No data is available for mapping function");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialog.show();
            }

            // Permission handling
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(MapsActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Snackbar.make(findViewById(R.id.mapID), "MyContactList requires this permission to locate your contacts",
                                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(MapsActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                                }
                            }).show();
                        } else {
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                        }
                    } else {
                        startLocationUpdates();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Error requesting permission", Toast.LENGTH_LONG).show();
            }

            RadioButton rbNormal = findViewById(R.id.radioButtonNormal);
            rbNormal.setChecked(true);
        }
    private void mapTypeButtons() {
        RadioGroup rgMapType = findViewById(R.id.radioGroupMapType);
        rgMapType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                RadioButton rbNormal = findViewById(R.id.radioButtonNormal);
                if (rbNormal.isChecked()) {
                    gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                }
                else {
                    gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }

        });
    }
    }

