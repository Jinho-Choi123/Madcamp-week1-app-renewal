package com.example.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Second_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Second_fragment extends Fragment {

    public Second_fragment() {
        // Required empty public constructor
    }

    public static Second_fragment newInstance() {
        Second_fragment fragment = new Second_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_fragment, container, false);
        ListView listview = (ListView) view.findViewById(R.id.db_contact_list);
        Contact.ContactAdapter adapter = new Contact.ContactAdapter();
        listview.setAdapter(adapter);


        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);

        String userId = sf.getString("userId", "");
        DB db = new DB(userId);

        db.Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Logger.log("111111111111111111111", "11111111111111111111");

                String data = snapshot.getValue().toString();
                Gson gson = new Gson();
                JsonObject jsonElement = (JsonObject) JsonParser.parseString(data);
                JsonArray contacts = (JsonArray) jsonElement.get("ContactList");
                Iterator iter = contacts.iterator();

                while(iter.hasNext()) {
                    Contact contact = gson.fromJson((JsonElement) iter.next(), Contact.class);
                    adapter.addItem(contact.getPhoneNumber(), contact.getName(), contact.getId());
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;


    }
}