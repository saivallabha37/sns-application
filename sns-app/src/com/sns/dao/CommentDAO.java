package com.sns.dao;

import com.sns.db.DBConnection;
import com.sns.model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    
    public boolean addComment(int postId, int userId, String content) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO comments (comment_id, content, created_at, user_id, post_id) " +
                        "VALUES (comment_seq.NEXTVAL, ?, SYSDATE, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, postId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Add comment error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Comment> getByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT c.comment_id, c.content, c.created_at, c.user_id, c.post_id, u.username " +
                        "FROM comments c, users u WHERE c.user_id = u.user_id AND c.post_id = ? " +
                        "ORDER BY c.created_at DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Comment comment = new Comment(
                    rs.getInt("comment_id"),
                    rs.getString("content"),
                    rs.getDate("created_at"),
                    rs.getInt("user_id"),
                    rs.getInt("post_id"),
                    rs.getString("username")
                );
                comments.add(comment);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Get comments error: " + e.getMessage());
        }
        return comments;
    }
    
    public boolean deleteComment(int commentId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM comments WHERE comment_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentId);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Delete comment error: " + e.getMessage());
            return false;
        }
    }
}
