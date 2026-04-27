package com.sns.model;

import java.sql.Date;

public class Comment {
    public int commentId;
    public String content;
    public Date createdAt;
    public int userId;
    public int postId;
    public String username;
    
    public Comment(int commentId, String content, Date createdAt, int userId, int postId, String username) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.postId = postId;
        this.username = username;
    }
    
    @Override
    public String toString() {
        return username + ": " + content;
    }
}
