package com.example.myapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DB {
    public String GoogleUserId;
    public DatabaseReference Ref;

    public DB (String userId){
        this.GoogleUserId = userId;
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference contactsRef = mRootRef.child("Contacts");
        this.Ref = contactsRef.child(this.GoogleUserId);
    }


    //get contactlist refering to given googleuserId
    public void fetch(Context context) {
        Logger.log("ddddddddddddddddddddaaaaaaaaaaaaaaaaaaaaaa"," data");
        ArrayList<Contact> db_contacts = new ArrayList<>();
        SharedPreferences sf = context.getSharedPreferences("googleAccount", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        this.Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String data = snapshot.getValue().toString();
                Logger.log("ddddddddddddddddddddaaaaaaaaaaaaaaaaaaaaaa", data);

                editor.putString("db_contacts", data);
                editor.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Gson gson = new Gson();
//        String data = sf.getString("db_contacts", "{}");
//        Logger.log("helllllllllllllllllllllllllllllllo", data);
//        JsonElement jsonObj = (JsonElement) JsonParser.parseString(data);
//        JsonArray contacts = (JsonArray) jsonObj.get("ContactList");
//        Iterator iter = contacts.iterator();

//        while(iter.hasNext()) {
//            Contact contact = gson.fromJson((JsonElement) iter.next(), Contact.class);
//            Logger.log("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz", contact.name);
//            db_contacts.add(contact);
//        }
//        Logger.log("ffffffffffffffffffffff", String.valueOf(db_contacts.size()));
    }

    public void post(ArrayList<Contact> local_contactList) {
        JsonObject data = new JsonObject();
        JsonArray result_contacts = new JsonArray();
        Gson gson = new Gson();
        String RESULT;
        Contact iter;


        for (int i = 0 ; i < local_contactList.size() ; i++) {
            iter = local_contactList.get(i);
            JsonObject obj = (JsonObject) gson.toJsonTree(iter);
            result_contacts.add(obj);
            }

        data.add("ContactList", result_contacts);
        data.addProperty("OwnerID", this.GoogleUserId);
        RESULT = gson.toJson(data);
        Ref.setValue(RESULT);
    }
}
