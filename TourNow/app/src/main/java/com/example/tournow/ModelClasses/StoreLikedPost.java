package com.example.tournow.ModelClasses;

public class StoreLikedPost {
    String userPhone;
    String postText;
    boolean liked;
    String imageUrl;
    String videoUrl;
    String postUserPhone;
    String postToken;

    public StoreLikedPost(String userPhone, String postText, boolean liked, String imageUrl, String videoUrl, String postUserPhone, String postToken) {
        this.userPhone = userPhone;
        this.postText = postText;
        this.liked = liked;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.postUserPhone = postUserPhone;
        this.postToken = postToken;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPostUserPhone() {
        return postUserPhone;
    }

    public void setPostUserPhone(String postUserPhone) {
        this.postUserPhone = postUserPhone;
    }

    public String getPostToken() {
        return postToken;
    }

    public void setPostToken(String postToken) {
        this.postToken = postToken;
    }
}
