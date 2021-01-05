package com.example.myapp;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Board_content {
    private String Title;
    private String Content;
    private String Date;
    private String Author;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Board_content () {
    }


    public void Board_content(String title, String content, String date, String author) {
        this.Title = title;
        this.Content = content;
        this.Date = date;
        this.Author = author;
    }

    public void setTitle(String title) {
        this.Title = title;
        return;
    }

    public String getTitle() {
        return this.Title;
    }



    public void setContent(String content) {
        this.Content = content;
        return ;
    }

    public String getContent() {
        return this.Content;
    }


    public void setAuthor(String author) {
        this.Author = author;
        return ;
    }

    public String getAuthor() {
        return this.Author;
    }
    public String getDate() {
        return this.Date.toString();
    }
    public void setDate(String date) {
        this.Date = date;
    }
}



