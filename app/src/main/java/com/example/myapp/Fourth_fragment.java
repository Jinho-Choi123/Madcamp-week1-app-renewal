package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fourth_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fourth_fragment extends Fragment {

    public Fourth_fragment() {
        // Required empty public constructor
    }

    public static Fourth_fragment newInstance(String param1, String param2) {
        Fourth_fragment fragment = new Fourth_fragment();
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

        View view = inflater.inflate(R.layout.fragment_fourth_fragment, container, false);
        


        BottomNavigationView board_bottom_nav = (BottomNavigationView) view.findViewById(R.id.board_nav_bar);
        board_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Board:
                        loadFragment(new Board_fragment());
                        break;

                    case R.id.MyPage:
                        loadFragment(new Board_myPage_fragment());
                        break;
                }
                return true;
            }
        });


        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.board_fragment, fragment);
        ft.commit();
    }
}