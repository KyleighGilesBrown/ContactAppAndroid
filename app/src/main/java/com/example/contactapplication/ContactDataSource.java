package com.example.contactapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
           // initialValues.put("editTextLastID", c.getEditTextLastID());
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
            //updateValues.put("editTextLastID", c.getEditTextLastID());
            updateValues.put("birth", String.valueOf(c.getBirth().getTimeInMillis()));

            didSucceed = database.update("contact", updateValues, "_id = " + rowId, null) > 0;
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
    public ArrayList<String> getContactName() {
        ArrayList<String> contactNames = new ArrayList<> ();
        try {
            String query = "Select editTextFirstID from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contactNames.add(cursor.getString(0));
                cursor.moveToNext();

        }
        cursor.close();


        }
        catch (Exception e) {
            contactNames = new ArrayList<String> ();

        }
        return contactNames;
    }
    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<Contact> ();
        try {
            String query = "SELECT * FROM contact";
            Cursor cursor = database.rawQuery(query, null);
            Contact newContact;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newContact = new Contact();
                newContact.setContactID(cursor.getInt(0));
                newContact.setEditTextFirstID(cursor.getString(1));
                newContact.setEditTextStrAddress(cursor.getString(2));
                newContact.setEditTextCityAddress(cursor.getString(3));
                newContact.setEditTextStateAddress(cursor.getString(4));
                newContact.setEditTextZipAddress(cursor.getString(5));
                newContact.setEditTextPhone(cursor.getString(6));
                newContact.setEditTextEmail(cursor.getString(7));
                newContact.setEditTextCountryAddress(cursor.getString(9));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(8)));
                newContact.setBirth(calendar);
                contacts.add(newContact);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            contacts = new ArrayList<Contact>();
        }
        return contacts;


            }


            public Contact getSpecificContact(int contactId) {
                Contact contact = new Contact();
                String query = "SELECT * FROM contact WHERE _id =" + contactId;
                Cursor cursor = database.rawQuery(query,null);

                if (cursor.moveToFirst()) {
                    contact.setContactID(cursor. getInt(0));
                    contact.setEditTextFirstID(cursor.getString(1));
                    contact.setEditTextStrAddress(cursor.getString(2));
                    contact.setEditTextCityAddress(cursor.getString(3));
                    contact.setEditTextStateAddress(cursor.getString(4));
                    contact.setEditTextZipAddress(cursor.getString(5));
                    contact.setEditTextPhone(cursor.getString(6));
                    contact.setEditTextEmail(cursor.getString(7));
                    contact.setEditTextCountryAddress(cursor.getString(9));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.valueOf(cursor.getString(8)));
                    contact.setBirth(calendar);

                    cursor.close();


                }
                return contact;

            }
            public boolean deleteContact(int contactId) {
                 boolean didDelete = false;
                 try {
                     didDelete = database.delete("contact", "_id=" + contactId, null) > 0;

                 }
                 catch (Exception e) {
                     //nothing needed here
                 }
                 return didDelete;
            }
        }



