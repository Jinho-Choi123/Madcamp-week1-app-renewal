package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
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
                String data;
                if(snapshot.getValue() == null) return;
                else data = snapshot.getValue().toString();
                Gson gson = new Gson();
                JsonObject jsonobj = (JsonObject) JsonParser.parseString(data);
                JsonArray contacts = (JsonArray) jsonobj.get("ContactList");
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


        BottomNavigationView refresh_bottomnav = (BottomNavigationView) view.findViewById(R.id.refresh_bottom_navbar);
        refresh_bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refresh_btn:
                        FragmentManager fm =( (MainActivity)getActivity()).getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment, new Second_fragment());
                        ft.commit();
                        break;
                }
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 String phoneNumber = ((TextView) view.findViewById(R.id.contact_phonenumber)).getText().toString();
                 String name = ((TextView) view.findViewById(R.id.contact_name)).getText().toString();

                 //check if it already exists
                Contact.ContactUtil contactutil = new Contact.ContactUtil();
                ArrayList<Contact> local_contacts = Contact.read(getContext());

                Iterator<Contact> iterator = local_contacts.iterator();
                Contact iter;
                while(iterator.hasNext()) {
                    iter = iterator.next();
                    Logger.log("check local contactsdddddddddddddd", iter.name);
                    if((iter.getPhoneNumber().equals(phoneNumber) ) && (iter.getName().equals(name))) {
                        Toast toast = Toast.makeText(getContext(), "Already Exists", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 500);
                        return;
                    }
                }

                 Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                 intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                 intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
                         .putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
                 startActivity(intent);
            }
        });



        return view;


    }
}