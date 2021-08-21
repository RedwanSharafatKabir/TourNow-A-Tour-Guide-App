package com.example.tournow.ModelClasses;

public class StorePostInfo {

    String userPhone;
    String name;
    String post;
    int likeNumbers;

    public StorePostInfo(String userPhone, String name, String post, int likeNumbers) {
        this.userPhone = userPhone;
        this.name = name;
        this.post = post;
        this.likeNumbers = likeNumbers;
    }

    public StorePostInfo() {
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getLikeNumbers() {
        return likeNumbers;
    }

    public void setLikeNumbers(int likeNumbers) {
        this.likeNumbers = likeNumbers;
    }
}
