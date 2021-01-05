package com.example.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Board_myPage_fragment extends Fragment {

    public Board_myPage_fragment() {
        // Required empty public constructor
    }

    public static Board_myPage_fragment newInstance(String param1, String param2) {
        Board_myPage_fragment fragment = new Board_myPage_fragment();
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

        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);
        Logger.log("11111111111","2222222222222");
        String userId = sf.getString("userId", "");
        Board_DB board_db = new Board_DB(userId);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_my_page_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLoginDialog();
                FragmentManager fm =( getActivity()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.board_fragment, new Board_myPage_fragment());
                ft.commit();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.BoardMypage_recyclerview);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Board_content> board_content = new ArrayList<Board_content>();
        RecyclerView.Adapter boardAdapter = new Board_myPage_Adapter(board_content, board_db, getContext());
        recyclerView.setAdapter(boardAdapter);



        Gson gson = new Gson();

        board_db.Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                board_content.clear();
                for(DataSnapshot board_snapshot : snapshot.getChildren()) {
                    Board_content content = board_snapshot.getValue(Board_content.class);
                    board_content.add(content);
                }
                boardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


    private void showLoginDialog() {
        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout = (LinearLayout) vi.inflate(R.layout.dialog, null);

        StringFilter stringFilter = new StringFilter(getActivity());
        InputFilter[] allowAlphanumericHangul = new InputFilter[1];
        allowAlphanumericHangul[0] = stringFilter.allowAlphanumericHangul;

        final EditText Title = (EditText)loginLayout.findViewById(R.id.title);
        final EditText Context = (EditText)loginLayout.findViewById(R.id.pcontext);
        Title.setFilters(allowAlphanumericHangul);
        Context.setFilters(allowAlphanumericHangul);


        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userEmail = sf.getString("userEmail", "");
        String userId = sf.getString("userId", "");


        new AlertDialog.Builder(getActivity()).setTitle("Posting").setView(loginLayout).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override public void onClick(DialogInterface dialog, int which)
                    {
                        Board_DB bdb = new Board_DB(userId);
                        Board_content b = new Board_content();
                        b.Board_content(Title.getText().toString(),
                                Context.getText().toString(), LocalDate.now().toString()
                                , userEmail);
                        bdb.writeNewPost(b);
                    }
                }).show();

    }


}