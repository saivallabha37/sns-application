package com.sns.dao;

import com.sns.db.DBConnection;
import com.sns.model.User;
import java.sql.*;

public class UserDAO {
    
    public boolean register(String username, String email, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (user_id, username, email, password) VALUES (user_seq.NEXTVAL, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }
    
    public User login(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT user_id, username, email FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"));
            }
            
            rs.close();
            pstmt.close();
            return user;
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            return null;
        }
    }
    
    public User getUserById(int userId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT user_id, username, email FROM users WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"));
            }
            
            rs.close();
            pstmt.close();
            return user;
        } catch (SQLException e) {
            System.err.println("Get user error: " + e.getMessage());
            return null;
        }
    }
}
