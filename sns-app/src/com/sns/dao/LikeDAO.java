package com.sns.dao;

import com.sns.db.DBConnection;
import java.sql.*;

public class LikeDAO {
    
    public void toggleLike(int postId, int userId) {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Check if like exists
            if (existsLike(postId, userId)) {
                // Delete like
                String deleteSql = "DELETE FROM like_table WHERE post_id = ? AND user_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteSql);
                pstmt.setInt(1, postId);
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                // Add like
                String insertSql = "INSERT INTO like_table (like_id, user_id, post_id) " +
                                  "VALUES (like_seq.NEXTVAL, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, postId);
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Toggle like error: " + e.getMessage());
        }
    }
    
    public int countByPost(int postId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) as total_likes FROM like_table WHERE post_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("total_likes");
            }
            rs.close();
            pstmt.close();
            
            return count;
        } catch (SQLException e) {
            System.err.println("Count likes error: " + e.getMessage());
            return 0;
        }
    }
    
    public boolean existsLike(int postId, int userId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) as cnt FROM like_table WHERE post_id = ? AND user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userId);
            
            ResultSet rs = pstmt.executeQuery();
            boolean exists = false;
            if (rs.next()) {
                exists = rs.getInt("cnt") > 0;
            }
            rs.close();
            pstmt.close();
            
            return exists;
        } catch (SQLException e) {
            System.err.println("Check like error: " + e.getMessage());
            return false;
        }
    }
}
