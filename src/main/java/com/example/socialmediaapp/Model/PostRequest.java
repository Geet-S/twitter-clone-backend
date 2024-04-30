package com.example.socialmediaapp.Model;


public class PostRequest {

    private int userID;
    private String postBody;

    // Constructors, getters, and setters

    public PostRequest() {
    }

    public PostRequest(int userID, String postBody) {
        this.userID = userID;
        this.postBody = postBody;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}