package com.example.contactapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class ContactDataSource {
    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    public ContactDataSource (Context context) {
        dbHelper = new ContactDBHelper(context);

    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertContact(Contact c) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("editTextEmail", c.getEditTextEmail());
            initialValues.put("editTextStrAddress", c.getEditTextStrAddress());
            initialValues.put("editTextCityAddress", c.getEditTextCityAddress());
            initialValues.put("editTextZipAddress", c.getEditTextZipAddress());
            initialValues.put("editTextStateAddress", c.getEditTextStateAddress());
            initialValues.put("editTextCountryAddress", c.getEditTextCountryAddress());
            initialValues.put("editTextPhone", c.getEditTextPhone());
            initialValues.put("editTextFirstID", c.getEditTextFirstID());
            initialValues.put("editTextLastID", c.getEditTextLastID());
            initialValues.put("birth", String.valueOf(c.getBirth().getTimeInMillis()));

            didSucceed = database.insert("contact", null, initialValues) > 0;
        }
        catch (Exception e) {

        }
        return didSucceed;

    }
    public boolean updateContact(Contact c) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) c.getContactID();
            ContentValues updateValues = new ContentValues();
            updateValues.put("editTextEmail", c.getEditTextEmail());
            updateValues.put("editTextStrAddress", c.getEditTextStrAddress());
            updateValues.put("editTextCityAddress", c.getEditTextCityAddress());
            updateValues.put("editTextZipAddress", c.getEditTextZipAddress());
            updateValues.put("editTextStateAddress", c.getEditTextStateAddress());
            updateValues.put("editTextCountryAddress", c.getEditTextCountryAddress());
            updateValues.put("editTextPhone", c.getEditTextPhone());
            updateValues.put("editTextFirstID", c.getEditTextFirstID());
            updateValues.put("editTextLastID", c.getEditTextLastID());
            updateValues.put("birth", String.valueOf(c.getBirth().getTimeInMillis()));

            didSucceed = database.insert("contact", null, updateValues) > 0;
        }
        catch (Exception e) {

        }
        return didSucceed;

    }
    public int getLastContactID() {
        int lastId;
        try {
            String query = "Select MAX(_id) from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch(Exception e) {
            lastId = -1;

        }
        return lastId;
    }
    }

