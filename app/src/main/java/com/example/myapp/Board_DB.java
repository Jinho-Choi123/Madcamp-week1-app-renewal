package com.example.myapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void writeNewPost(Board_content board_content) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        this.Ref.child(board_content.getTitle()).setValue(board_content);
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
