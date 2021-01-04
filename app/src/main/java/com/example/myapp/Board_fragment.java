package com.example.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Board_fragment extends Fragment {

    public Board_fragment() {
        // Required empty public constructor
    }

    public static Board_fragment newInstance(String param1, String param2) {
        Board_fragment fragment = new Board_fragment();
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
        View view =  inflater.inflate(R.layout.fragment_board_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.BoardContent_recyclerview);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Board_content> board_content = new ArrayList<Board_content>();
        RecyclerView.Adapter boardAdapter = new Board_Adapter(board_content);
        recyclerView.setAdapter(boardAdapter);

        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userId = sf.getString("userId", "");
        Board_DB board_db = new Board_DB(userId);

        Gson gson = new Gson();

        board_db.board_Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Logger.log("helllllllllllllllo world", "!!!!!!!!!!!!!!!!!");
                for (DataSnapshot snapshot_ : snapshot.getChildren()) {
                    Logger.log("helllllllllllllllo by!!", "bbbbbbbbbbbbbb");
                    for(DataSnapshot board_snapshot : snapshot_.getChildren()) {
                        Logger.log("helllllllllllllllo by!!", "ccccccccccccccccc");
                        Board_content content= board_snapshot.getValue(Board_content.class);
                        Logger.log("ggggggggggggggg", content.getContent());
                        board_content.add(content);
                    }
                }
                boardAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}