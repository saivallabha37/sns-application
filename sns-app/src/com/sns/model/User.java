package com.sns.model;

public class User {
    public int userId;
    public String username;
    public String email;
    public String password;
    
    public User(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
    
    public User(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    @Override
    public String toString() {
        return username + " (" + email + ")";
    }
}
