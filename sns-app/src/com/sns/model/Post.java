package com.sns.model;

import java.sql.Date;

public class Post {
    public int postId;
    public String content;
    public Date createdAt;
    public int userId;
    public String username;
    public int likeCount;
    public int commentCount;
    
    public Post(int postId, String content, Date createdAt, int userId, String username) {
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.username = username;
        this.likeCount = 0;
        this.commentCount = 0;
    }
    
    @Override
    public String toString() {
        return "Post [" + postId + "] by " + username + ": " + content;
    }
}
