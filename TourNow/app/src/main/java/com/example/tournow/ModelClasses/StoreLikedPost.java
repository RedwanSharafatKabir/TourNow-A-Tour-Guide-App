package com.example.tournow.ModelClasses;

public class StoreLikedPost {
    String userPhone;
    String postText;
    boolean liked;

    public StoreLikedPost(String userPhone, String postText, boolean liked) {
        this.userPhone = userPhone;
        this.postText = postText;
        this.liked = liked;
    }

    public StoreLikedPost() {
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
