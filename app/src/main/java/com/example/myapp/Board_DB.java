package com.example.myapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Board_DB {
    public String GoogleUserId;
    public DatabaseReference Ref;
    public DatabaseReference board_Ref;

    public Board_DB(String userId) {
        this.GoogleUserId = userId;
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        this.board_Ref = mRootRef.child("Boards");
        this.Ref = this.board_Ref.child(this.GoogleUserId);
    }

    public void post(Board_content boardContent) {
        JsonObject data = new JsonObject();
        Gson gson = new Gson();
        String RESULT;
        data.addProperty("Content", boardContent.getContent());
        data.addProperty("Author", boardContent.getAuthor());
        data.addProperty("Date", boardContent.getDate());
        data.addProperty("Title", boardContent.getTitle());
        RESULT = gson.toJson(data);
        this.Ref.child(boardContent.getTitle()).setValue(RESULT);
    }

//    public ArrayList<Board_content> read_all() {
//        //read all board contents from db
//        this.board_Ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
