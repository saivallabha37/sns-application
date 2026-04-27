package com.sns.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {
    private static Connection conn = null;
    
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream("db.properties");
                props.load(fis);
                fis.close();
                
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("✓ Database connected successfully!");
            } catch (IOException e) {
                System.err.println("db.properties file not found!");
                e.printStackTrace();
                throw new SQLException("Connection failed");
            }
        }
        return conn;
    }
    
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✓ Database disconnected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
