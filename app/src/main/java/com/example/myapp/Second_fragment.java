package com.example.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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

    public static Second_fragment newInstance(String param1) {
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

        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userId = sf.getString("userId", "");
        DB db = new DB(userId);
        db.fetch(getContext());

        ArrayList<Contact> db_contacts = new ArrayList<>();
        ArrayList<Contact> contacts = Contact.read(getContext());
        db.fetch(getContext());
        Gson gson = new Gson();
        String data = sf.getString("db_contacts", "{}");
        Logger.log("helllllllllllllllllllllllllllllllo", data);
        JsonObject jsonObj = (JsonObject) JsonParser.parseString(data);
        JsonArray contacts_ = (JsonArray) jsonObj.get("ContactList");
        Iterator iter = contacts_.iterator();

        while(iter.hasNext()) {
            Contact contact = gson.fromJson((JsonElement) iter.next(), Contact.class);
            Logger.log("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz", contact.name);
            db_contacts.add(contact);
        }

        ArrayList<Contact> local_contacts = Contact.read(getContext());
        Contact.ContactAdapter adapter = new Contact.ContactAdapter();

        ArrayList<Contact> result_contacts = db_contacts;
        //filterout existing contact

        listview.setAdapter(adapter);
        Contact contact_iter;
        for(int i=0 ; i < result_contacts.size() ; i ++) {
            contact_iter = result_contacts.get(i);
            adapter.addItem(contact_iter.getPhoneNumber(),contact_iter.getName(), contact_iter.getId());
        }

        adapter.notifyDataSetChanged();
        return view;
    }
}