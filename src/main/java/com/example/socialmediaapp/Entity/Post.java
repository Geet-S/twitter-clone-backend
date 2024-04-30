package com.example.socialmediaapp.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false, length = 1000)
    private String postBody;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    // Constructors, getters, and setters

    public Post() {
        this.date = new Date();
    }

    public Post(int userId, String postBody) {
        this.userId = userId;
        this.postBody = postBody;
        this.date = new Date();
    }

    public int getPostId() {
        return postId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Date getDate() {
        return date;
    }
}
