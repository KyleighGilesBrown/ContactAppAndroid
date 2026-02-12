package com.example.contactapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter {
    private ArrayList<Contact> contactData;
    public View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public ContactAdapter(ArrayList<Contact> arrayList, Context context) {
        contactData = arrayList;
        parentContext = context;
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewContact;
        public TextView textPhone;
        public TextView textEmail;

        public Button deleteButton;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            textEmail = itemView.findViewById(R.id.textEmail);
            deleteButton =  itemView.findViewById(R.id.buttonDeleteContact);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getContactTextView() {
            return textViewContact;
        }
        public TextView getPhoneTextView() {
            return textPhone;
        }

        public TextView getEmailTextView() {
            return textEmail;
        }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }
    public ContactAdapter (ArrayList<Contact> arrayList) {
        contactData = arrayList;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;

//        for(int i =0; i < position; i++) {
//            if (i % 2 != 0) {
//                cvh.itemView.setBackgroundColor(Color.RED);
//            }
//            else {
//                cvh.itemView.setBackgroundColor(Color.BLUE);
//            }
//        }
        cvh.getContactTextView().setText(contactData.get(position).getEditTextFirstID());
        cvh.getPhoneTextView().setText(contactData.get(position).getEditTextPhone());
        cvh.getEmailTextView().setText(contactData.get(position).getEditTextEmail());


        if (isDeleting) {
            cvh.getDeleteButton().setVisibility(View.VISIBLE);



            cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteItem(position);

                }
            });
        }
        else {
            cvh.getDeleteButton().setVisibility(View.INVISIBLE);

        }



    }
    public void setDelete(boolean b) {
        isDeleting = b;
    }
    private void deleteItem(int position) {
        Contact contact = contactData.get(position);
        ContactDataSource ds = new ContactDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteContact(contact.getContactID());
            ds.close();
            if (didDelete) {
                contactData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }
}
