package com.example.myapp;

import android.net.Uri;

public class ImageModel {

    private String image;
    private String title;
    private int resImg;
    private boolean isSelected;
    private Uri uri;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResImg() {
        return resImg;
    }

    public void setResImg(int resImg) {
        this.resImg = resImg;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri(){
        return this.uri;
    }


}