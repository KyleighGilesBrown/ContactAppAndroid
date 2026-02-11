package com.example.contactapplication;
import java.util.Calendar;

public class Contact {
    private int contactID;
    private String editTextEmail;
    private String editTextStrAddress;
    private String editTextCityAddress;
    private String editTextZipAddress;

    private String editTextStateAddress;
    private String editTextCountryAddress;
    private String editTextPhone;
    private String editTextFirstID;

   // private String editTextLastID;

    private Calendar birth;

    public Contact() {
        contactID = -1;
        birth = Calendar.getInstance();
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int i) {
        contactID = i;
    }

    public String getEditTextEmail() {
        return editTextEmail;
    }

    public void setEditTextEmail(String editTextEmail) {
        this.editTextEmail = editTextEmail;
    }

    public String getEditTextStrAddress() {
        return editTextStrAddress;
    }

    public void setEditTextStrAddress(String editTextStrAddress) {
        this.editTextStrAddress = editTextStrAddress;
    }

    public String getEditTextCityAddress() {
        return editTextCityAddress;
    }

    public void setEditTextCityAddress(String editTextCityAddress) {
        this.editTextCityAddress = editTextCityAddress;
    }

    public String getEditTextZipAddress() {
        return editTextZipAddress;
    }

    public void setEditTextZipAddress(String editTextZipAddress) {
        this.editTextZipAddress = editTextZipAddress;
    }

    public String getEditTextStateAddress() {
        return editTextStateAddress;
    }

    public void setEditTextStateAddress(String editTextStateAddress) {
        this.editTextStateAddress = editTextStateAddress;
    }

    public String getEditTextCountryAddress() {
        return editTextCountryAddress;
    }

    public void setEditTextCountryAddress(String editTextCountryAddress) {
        this.editTextCountryAddress = editTextCountryAddress;
    }

    public String getEditTextPhone() {
        return editTextPhone;
    }

    public void setEditTextPhone(String editTextPhone) {
        this.editTextPhone = editTextPhone;
    }

    public String getEditTextFirstID() {
        return editTextFirstID;
    }

    public void setEditTextFirstID(String editTextFirstID) {
        this.editTextFirstID = editTextFirstID;
    }

   // public String getEditTextLastID() {
//        return editTextLastID;
//    }
//
//    public void setEditTextLastID(String editTextLastID) {
//        this.editTextLastID = editTextLastID;
//    }

    public Calendar getBirth() {
        return birth;
    }

    public void setBirth(Calendar birth) {
        this.birth = birth;
    }
}
