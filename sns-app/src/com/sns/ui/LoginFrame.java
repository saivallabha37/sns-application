package com.sns.ui;

import com.sns.dao.UserDAO;
import com.sns.model.User;
import com.sns.util.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registerBtn;
    
    public LoginFrame() {
        setTitle("SNS - Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");
        
        panel.add(loginBtn);
        panel.add(registerBtn);
        
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();
            }
        });
        
        add(panel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);
        
        if (user != null) {
            SessionManager.setCurrentUser(user);
            dispose();
            new FeedFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
