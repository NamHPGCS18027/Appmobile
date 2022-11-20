package com.example.applicationmobile;

public class uploadImage {
    private String mImageName;
    private String mImageUrl;

    public uploadImage(){

    }

    public uploadImage(String name , String imageUrl){
        if (name.trim().equals("")){
            name = "No Name";
        }
        mImageName = name;
        mImageUrl = imageUrl;
    }

    public String getmImageName() {
        return mImageName;
    }

    public void setmImageName(String mImageName) {
        this.mImageName = mImageName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
