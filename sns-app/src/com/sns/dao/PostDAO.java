package com.sns.dao;

import com.sns.db.DBConnection;
import com.sns.model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    
    public boolean createPost(String content, int userId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO post (post_id, content, created_at, user_id) VALUES (post_seq.NEXTVAL, ?, SYSDATE, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.setInt(2, userId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Create post error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT p.post_id, p.content, p.created_at, p.user_id, u.username " +
                        "FROM post p, users u WHERE p.user_id = u.user_id " +
                        "ORDER BY p.created_at DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Post post = new Post(
                    rs.getInt("post_id"),
                    rs.getString("content"),
                    rs.getDate("created_at"),
                    rs.getInt("user_id"),
                    rs.getString("username")
                );
                posts.add(post);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Get all posts error: " + e.getMessage());
        }
        return posts;
    }
    
    public List<Post> getUserPosts(int userId) {
        List<Post> posts = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT p.post_id, p.content, p.created_at, p.user_id, u.username " +
                        "FROM post p, users u WHERE p.user_id = u.user_id AND p.user_id = ? " +
                        "ORDER BY p.created_at DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Post post = new Post(
                    rs.getInt("post_id"),
                    rs.getString("content"),
                    rs.getDate("created_at"),
                    rs.getInt("user_id"),
                    rs.getString("username")
                );
                posts.add(post);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Get user posts error: " + e.getMessage());
        }
        return posts;
    }
    
    public boolean deletePost(int postId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM post WHERE post_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Delete post error: " + e.getMessage());
            return false;
        }
    }
}
