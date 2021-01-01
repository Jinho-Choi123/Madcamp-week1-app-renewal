package com.example.myapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DB {
    private Long GoogleUserId;

    public DB (Long userId){
        this.GoogleUserId = userId;
    }
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference contactsRef = mRootRef.child("Contacts");

    //get contactlist refering to given googleuserId
    public ArrayList<Contact> fetch() {
        ArrayList<Contact> db_contact_list = new ArrayList<Contact>();

        contactsRef.child(this.GoogleUserId.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Gson gson = new Gson();
                String data = snapshot.getValue().toString();
                JsonObject jsonObj = gson.fromJson(data, JsonObject.class);
                JsonArray db_contact_list_json = jsonObj.getAsJsonArray("ContactList");
                Iterator<JsonElement> iter = db_contact_list_json.iterator();

                JsonObject iter_json = new JsonObject();
                while(iter.hasNext()) {
                    iter_json = iter.next().getAsJsonObject();
                    Contact contact = gson.fromJson(iter_json, Contact.class);
                    db_contact_list.add(contact);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return db_contact_list;
    }

    public void post(ArrayList<Contact> contactList) {
        JsonObject data = new JsonObject();
        JsonArray local_contact_list = new JsonArray();
        Gson gson = new Gson();
        String RESULT;
        Contact iter;

        for (int i = 0 ; i < contactList.size() ; i++) {
            iter = contactList.get(i);
            JsonObject obj = (JsonObject) gson.toJsonTree(iter);
            local_contact_list.add(obj);

        }
        data.add("ContactList", local_contact_list);
        data.addProperty("OwnerID", this.GoogleUserId);
        RESULT = gson.toJson(data);
        contactsRef.child(this.GoogleUserId.toString()).setValue(RESULT);
    }
}
