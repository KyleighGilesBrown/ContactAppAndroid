package com.example.contactapplication;


import static androidx.core.content.SharedPreferencesKt.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        contactListButton();
        settingsButton();
        mapButton();
        sortByClick();
        sortOrderClick();
    }
    private void contactListButton() {
        ImageButton clButton = findViewById(R.id.listButtonID2);
        clButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ContactListActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void mapButton() {
        ImageButton mButton = findViewById(R.id.mapButtonID2);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MapsActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void settingsButton() {
        ImageButton sButton = findViewById(R.id.settingButtonID2);
        sButton.setEnabled(false);
    }
    private void saveSettings() {
        String sortBy = getSharedPreferences("MySortingPreferences",
                Context.MODE_PRIVATE).getString("group1ID", "radioNameID");
        String sortOrder = getSharedPreferences("MySortingPreferences",
                Context.MODE_PRIVATE).getString("group2ID", "radioAscID");

        RadioButton rbName = findViewById(R.id.radioNameID);
        RadioButton rbCity = findViewById(R.id.radioCityID);
        RadioButton rbBirthday = findViewById(R.id.radioBirthdayID);

        if (sortBy.equalsIgnoreCase("radioNameID")) {
            rbName.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("radioCityID")) {
            rbCity.setChecked(true);
        } else {
            rbBirthday.setChecked(true);
        }

        RadioButton asc = findViewById(R.id.radioAscID);
        RadioButton desc = findViewById(R.id.radioDescID);
        if (sortOrder.equalsIgnoreCase("radioAscID")) {
            asc.setChecked(true);
        } else {
            desc.setChecked(true);
        }
    }

        private void sortByClick() {
            RadioGroup rgSortBy = findViewById(R.id.group1ID);
            rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                    RadioButton rbName = findViewById(R.id.radioNameID);
                    RadioButton rbCity = findViewById(R.id.radioCityID);
                    if (rbName.isChecked()) {
                        getSharedPreferences("MySortingPreferences", Context.MODE_PRIVATE)
                                .edit().putString("group1ID", "radioNameID").apply();
                    } else if (rbCity.isChecked()) {
                        getSharedPreferences("MySortingPreferences",
                                Context.MODE_PRIVATE)
                                .edit().putString("group1ID", "radioCityID").apply();
                    } else {
                        getSharedPreferences("MySortingPreferences", Context.MODE_PRIVATE)
                                .edit().putString("group1ID", "radioBirthdayID").apply();
                    }
                }
            });
        }

        private void sortOrderClick() {
            RadioGroup rgSortBy = findViewById(R.id.group1ID);
            rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                    RadioButton asc = findViewById(R.id.radioAscID);
                    if (asc.isChecked()) {
                        getSharedPreferences("MySortingPreferences", Context.MODE_PRIVATE)
                                .edit().putString("group2ID", "radioAscID").apply();

                    } else {
                        getSharedPreferences("MySortingPreferences", Context.MODE_PRIVATE)
                                .edit().putString("group2ID", "radioDescID").apply();
                    }
                }
            });
    }
}
