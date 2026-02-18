package com.example.contactapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;


import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {
    private Contact currentContact;
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
        changeDateButton();
        editButton();

        saveContactButton();
        initTextChangeEvents();
        hideKeyboard();

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
                if (currentContact.getContactID() == -1) {

                    Toast.makeText(getBaseContext(), "Contact must be saved before it can be mapped", Toast.LENGTH_LONG).show();
                }
                else {
                    intent.putExtra("contactid", currentContact.getContactID());
                }
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void editButton() {
        final Button editButton = findViewById(R.id.editSwitchID);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initContact(extras.getInt("contactid"));

        }
        else {
            currentContact = new Contact();
        }
        setForEditing(false);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editButton.setSelected(!editButton.isSelected());
                setForEditing(editButton.isSelected());
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
       // EditText lastNameText = findViewById(R.id.editTextLastID);
        Button changeBirthButton = findViewById(R.id.changeBirthButtonID);
        Button saveButton = findViewById(R.id.saveButtonID);

        emailText.setEnabled(enabled);
        phoneText.setEnabled(enabled);
        stateText.setEnabled(enabled);
        streetText.setEnabled(enabled);
        cityText.setEnabled(enabled);
        countryText.setEnabled(enabled);
        zipText.setEnabled(enabled);
        firstNameText.setEnabled(enabled);
       // lastNameText.setEnabled(enabled);
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

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView birthdayText = findViewById(R.id.birthViewID);
        String dateString = (selectedTime.get(Calendar.MONTH) + 1) + "/" +
                selectedTime.get(Calendar.DAY_OF_MONTH) + "/" +
                selectedTime.get(Calendar.YEAR);

        birthdayText.setText(dateString);
        currentContact.setBirth(selectedTime);
    }

    private void initTextChangeEvents() {
        final EditText etContactFName = findViewById(R.id.editTextFirstID);
        etContactFName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextFirstID(etContactFName.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

        final EditText etStreetAddress = findViewById(R.id.editTextStrAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextStrAddress(etStreetAddress.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

//        final EditText etContactLName = findViewById(R.id.editTextLastID);
//            etContactLName.addTextChangedListener(new TextWatcher() {
//                public void afterTextChanged(Editable s) {
//                    currentContact.setEditTextLastID(etContactLName.getText().toString());
//                }
//                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//                }
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//            });
        final EditText etZip = findViewById(R.id.editTextZipAddress);
        etZip.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextZipAddress(etZip.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });
        final EditText etCountry = findViewById(R.id.editTextCountryAddress);
        etCountry.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextCountryAddress(etCountry.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

        final EditText etEmail = findViewById(R.id.editTextEmail);
        etEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextEmail(etEmail.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

        final EditText etPhoneNum = findViewById(R.id.editTextPhone);
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextPhone(etPhoneNum.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });
        etPhoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        final EditText etState = findViewById(R.id.editTextStateAddress);
        etState.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextStateAddress(etState.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });
        final EditText etCity = findViewById(R.id.editTextCityAddress);
        etCity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentContact.setEditTextCityAddress(etCity.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });




    }
    private void saveContactButton() {
        Button saveContact = findViewById(R.id.saveButtonID);
        saveContact.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                ContactDataSource ds = new ContactDataSource(MainActivity.this);
                try{
                    ds.open();
                    if(currentContact.getContactID() == -1) {
                        wasSuccessful = ds.insertContact(currentContact);
                        if (wasSuccessful){
                        int newId = ds.getLastContactID();
                        currentContact.setContactID(newId); }
                    }
                    else {
                        wasSuccessful = ds.updateContact(currentContact);

                    }
                    ds.close();

                }
                catch (Exception e){
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    Button editButton = findViewById(R.id.editSwitchID);
                    editButton.setSelected(!editButton.isSelected());
                    setForEditing(false);

                }
                }
            });
        }

        private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editFName = findViewById(R.id.editTextFirstID);
        imm.hideSoftInputFromWindow(editFName.getWindowToken(),0);
//        EditText editLName = findViewById(R.id.editTextLastID);
//        imm.hideSoftInputFromWindow(editLName.getWindowToken(),0);

            EditText editEmail = findViewById(R.id.editTextEmail);
            imm.hideSoftInputFromWindow(editEmail.getWindowToken(),0);
            EditText editPhone = findViewById(R.id.editTextPhone);
            imm.hideSoftInputFromWindow(editPhone.getWindowToken(),0);
            EditText editStr = findViewById(R.id.editTextStrAddress);
            imm.hideSoftInputFromWindow(editStr.getWindowToken(),0);

            EditText editState = findViewById(R.id.editTextStateAddress);
            imm.hideSoftInputFromWindow(editState.getWindowToken(),0);
            EditText editCity = findViewById(R.id.editTextCityAddress);
            imm.hideSoftInputFromWindow(editCity.getWindowToken(),0);
            EditText editCountry = findViewById(R.id.editTextCountryAddress);
            imm.hideSoftInputFromWindow(editCountry.getWindowToken(),0);
            //not sure to include birthday here?
        }

        private void initContact(int id) {
            ContactDataSource ds = new ContactDataSource(MainActivity.this);
            try {
                ds.open();
                currentContact = ds.getSpecificContact(id);
                ds.close();

            }

            catch(Exception e) {
                Toast.makeText(this,"Load Contact Failed", Toast.LENGTH_LONG).show();

            }

            EditText editName = findViewById(R.id.editTextFirstID);
            EditText editStrAddress = findViewById(R.id.editTextStrAddress);
            EditText editCity = findViewById(R.id.editTextCityAddress);
            EditText editState = findViewById(R.id.editTextStateAddress);
            EditText editZipCode = findViewById(R.id.editTextZipAddress);
            EditText editCountry = findViewById(R.id.editTextCountryAddress);
            EditText editPhone = findViewById(R.id.editTextPhone);
            EditText editEmail = findViewById(R.id.editTextEmail);
            TextView birthday = findViewById(R.id.birthViewID);

            editName.setText(currentContact.getEditTextFirstID());
            editStrAddress.setText(currentContact.getEditTextStrAddress());
            editCity.setText(currentContact.getEditTextCityAddress());
            editState.setText(currentContact.getEditTextStateAddress());
            editZipCode.setText(currentContact.getEditTextZipAddress());
            editCountry.setText(currentContact.getEditTextCountryAddress());
            editPhone.setText(currentContact.getEditTextPhone());
            editEmail.setText(currentContact.getEditTextEmail());
            birthday.setText(DateFormat.format("MM/dd/yyyy", currentContact.getBirth().getTimeInMillis()).toString());

        }
        }




