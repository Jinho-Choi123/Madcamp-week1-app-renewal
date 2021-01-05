package com.example.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class First_fragment extends Fragment {

    Bundle bundle ;
    public First_fragment() {
        // Required empty public constructor
    }

    public static First_fragment newInstance() {
        First_fragment fragment = new First_fragment();
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

        View view = inflater.inflate(R.layout.fragment_first_fragment, container, false);
        ArrayList<Contact> contacts = Contact.read(getContext());

        ListView listview = (ListView) view.findViewById(R.id.local_contact_list);
        Contact.ContactAdapter adapter = new Contact.ContactAdapter();

        listview.setAdapter(adapter);

        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userId = sf.getString("userId", "");
        DB db = new DB(userId);

        Contact iter;

        for(int i = 0 ; i < contacts.size() ; i ++) {
            iter = contacts.get(i);
            adapter.addItem(iter.getPhoneNumber(), iter.getName(), iter.getId(), false);
        }
        adapter.notifyDataSetChanged();

        BottomNavigationView upload_bottomnav = (BottomNavigationView) view.findViewById(R.id.upload_bottom_navbar);
        upload_bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload_btn:
                        db.post(contacts);
                        break;
                }
                return true;
            }
        });
        return view;
    }

}

