package com.example.socialmediaapp.Model;

public class CommentRequest {

    private int postID;
    private int userID;
    private String commentBody;

    public CommentRequest() {
    }

    public CommentRequest(int postId, int userID, String commentBody) {
        this.postID = postID;
        this.userID = userID;
        this.commentBody = commentBody;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userId) {
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
