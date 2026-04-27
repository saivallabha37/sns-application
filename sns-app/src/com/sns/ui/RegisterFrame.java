package com.sns.ui;

import com.sns.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerBtn;
    private JButton cancelBtn;
    
    public RegisterFrame() {
        setTitle("SNS - Register");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);
        
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        panel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);
        
        registerBtn = new JButton("Register");
        cancelBtn = new JButton("Cancel");
        
        panel.add(registerBtn);
        panel.add(cancelBtn);
        
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        add(panel);
        setVisible(true);
    }
    
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username must be at least 3 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.register(username, email, password);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed! Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
