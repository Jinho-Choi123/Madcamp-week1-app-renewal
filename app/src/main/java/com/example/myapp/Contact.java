package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Contact {
    String id;
    String phoneNumber;
    String name;
    Boolean setGray;

    public void Contact(String id, String phoneNumber, String name, Boolean setGray) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.setGray = setGray;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getSetGray() { return this.setGray;}
    public void setSetGray(Boolean setGray) {this.setGray = setGray;}

    public static class ContactAdapter extends BaseAdapter {
        private TextView phoneNumber;
        private TextView name;
        private LinearLayout contact_data_set;
        private ArrayList<Contact> contact_list = new ArrayList<Contact>();

        public ContactAdapter() {

        }

        @Override
        public int getCount() {
            return contact_list.size();
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_contact, parent, false);
            }
            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            layoutParams.height = 150;
            convertView.setLayoutParams(layoutParams);
            phoneNumber = (TextView) convertView.findViewById(R.id.contact_phonenumber);
            name = (TextView) convertView.findViewById(R.id.contact_name);
            //id = (TextView) convertView.findViewById(R.id.contact_id);
            contact_data_set = (LinearLayout) convertView.findViewById(R.id.contact_data_set);

            Contact item = contact_list.get(position);

            phoneNumber.setText(item.getPhoneNumber());
            name.setText(item.getName());
            if(item.setGray == true) {
                contact_data_set.setBackgroundColor(Color.parseColor("#D5DBDB"));
            }
            //id.setText(Long.toString(item.getId()));
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return contact_list.get(position);
        }

        public void addItem(String phonenumber, String name, String id, Boolean setGray) {
            Contact item = new Contact();

            item.setId(id);
            item.setName(name);
            item.setPhoneNumber(phonenumber);
            item.setSetGray(setGray);
            contact_list.add(item);
        }
        public void deleteAll() {
        }
    }

    public static class ContactUtil {

        public ContactUtil() {
        }

        public ArrayList<Contact> getContactList(Context context) {

            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            String[] projection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID, // 연락처 ID -> 사진 정보 가져오는데 사용
                    ContactsContract.CommonDataKinds.Phone.NUMBER,        // 연락처
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}; // 연락처 이름.

            String[] selectionArgs = null;

            String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

            Cursor contactCursor = context.getContentResolver().query(uri, projection, null, selectionArgs, sort);

            ArrayList<Contact> contactlist = new ArrayList<Contact>();


            if (contactCursor.moveToFirst()) {
                do {
                    String phonenumber = contactCursor.getString(1).replaceAll("-", "");
                    if (phonenumber.length() == 10) {
                        phonenumber = phonenumber.substring(0, 3) + "-"
                                + phonenumber.substring(3, 6) + "-"
                                + phonenumber.substring(6);
                    } else if (phonenumber.length() > 8) {
                        phonenumber = phonenumber.substring(0, 3) + "-"
                                + phonenumber.substring(3, 7) + "-"
                                + phonenumber.substring(7);
                    }

                    Contact contact = new Contact();
                    contact.setId(contactCursor.getString(0));
                    contact.setPhoneNumber(phonenumber);
                    contact.setName(contactCursor.getString(2));
                    contactlist.add(contact);

                } while (contactCursor.moveToNext());
            }
            return contactlist;
        }
    }

    //store contact to local phone
    public static void store(Context context, Contact contact) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, contact.name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, contact.phoneNumber);

        context.startActivity(intent);
    }

    //read contact list of local phone
    public static ArrayList<Contact> read(Context context){
        ContactUtil contactUtil = new ContactUtil();
        ArrayList<Contact> result = contactUtil.getContactList(context);
        return result;
    }
}
