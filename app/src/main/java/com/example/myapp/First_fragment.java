package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class First_fragment extends Fragment {

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

        View view = inflater.inflate(R.layout.fragment_first_fragment, null);

        ArrayList<Contact> contacts = Contact.read(getContext());

        ListView listview = (ListView) view.findViewById(R.id.local_contact_list);
        Contact.ContactAdapter adapter = new Contact.ContactAdapter();

        listview.setAdapter(adapter);

        Contact iter;

        for(int i = 0 ; i < contacts.size() ; i ++) {
            iter = contacts.get(i);
            adapter.addItem(iter.getPhoneNumber(), iter.getName(), iter.getId());
        }
        adapter.notifyDataSetChanged();

        return view;
    }
}

