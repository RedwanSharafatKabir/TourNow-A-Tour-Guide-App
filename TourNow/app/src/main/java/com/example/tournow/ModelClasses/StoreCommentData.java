package com.example.tournow.ModelClasses;

public class StoreCommentData {
    String commenterPhone;
    String commenterName;
    String commentText;

    public StoreCommentData(String commenterPhone, String commenterName, String commentText) {
        this.commenterPhone = commenterPhone;
        this.commenterName = commenterName;
        this.commentText = commentText;
    }

    public StoreCommentData() {
    }

    public String getCommenterPhone() {
        return commenterPhone;
    }

    public void setCommenterPhone(String commenterPhone) {
        this.commenterPhone = commenterPhone;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
