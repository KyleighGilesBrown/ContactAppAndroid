package com.example.contactapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        contactListButton();
        settingsButton();
        mapButton();
    }

    private void contactListButton() {
        ImageButton clButton = findViewById(R.id.listButtonID);
        clButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void settingsButton() {
        ImageButton sButton = findViewById(R.id.settingButtonID);
        sButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void mapButton() {
        ImageButton mButton = findViewById(R.id.mapButtonID);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void editButton() {
        final Button editButton = findViewById(R.id.editSwitchID);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForEditing(editButton.isEnabled());
            }
        });
    }
    private void setForEditing(boolean enabled) {

        EditText emailText = findViewById(R.id.editTextEmail);
        EditText phoneText = findViewById(R.id.editTextPhone);
        EditText streetText = findViewById(R.id.editTextStrAddress);
        EditText cityText = findViewById(R.id.editTextCityAddress);
        EditText stateText = findViewById(R.id.editTextStateAddress);
        EditText countryText = findViewById(R.id.editTextCountryAddress);
        EditText zipText = findViewById(R.id.editTextZipAddress);
        EditText firstNameText = findViewById(R.id.editTextFirstID);
        EditText lastNameText = findViewById(R.id.editTextLastID);
        Button changeBirthButton = findViewById(R.id.changeBirthButtonID);
        Button saveButton = findViewById(R.id.saveButtonID);

        emailText.setEnabled(enabled);
        phoneText.setEnabled(enabled);
        streetText.setEnabled(enabled);
        cityText.setEnabled(enabled);
        countryText.setEnabled(enabled);
        zipText.setEnabled(enabled);
        firstNameText.setEnabled(enabled);
        lastNameText.setEnabled(enabled);
        changeBirthButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);

        if (enabled) {
            firstNameText.requestFocus();
        }

    }
    private void changeDateButton() {
        Button changeDate = findViewById(R.id.changeBirthButtonID);
        changeDate.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");
            }
        });
    }
}


