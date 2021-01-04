package com.example.myapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;

public class Board_DB {
    public String GoogleUserId;
    public DatabaseReference Ref;
    public DatabaseReference mRootRef;
    public DatabaseReference boardsRef;
    public Board_DB(String userId){
        this.GoogleUserId = userId;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        boardsRef = mRootRef.child("Boards");
        this.Ref = boardsRef.child(this.GoogleUserId);

    }

    public void writeNewPost(String title, String author, String content, String date) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        Board_content posts = new Board_content();
        posts.setTitle(title);
        posts.setAuthor(author);
        posts.setContent(content);
        posts.setDate(date);
        this.Ref.child(title).setValue(posts);
    }
/*
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
*/
}
