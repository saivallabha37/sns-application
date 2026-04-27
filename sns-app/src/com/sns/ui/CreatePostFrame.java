package com.sns.ui;

import com.sns.dao.PostDAO;
import com.sns.util.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePostFrame extends JFrame {
    private JTextArea contentArea;
    private JButton postBtn;
    private JButton cancelBtn;
    private PostDAO postDAO;
    
    public CreatePostFrame() {
        setTitle("SNS - Create Post");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        postDAO = new PostDAO();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("What's on your mind?"), BorderLayout.NORTH);
        
        contentArea = new JTextArea(10, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        postBtn = new JButton("Post");
        cancelBtn = new JButton("Cancel");
        
        buttonPanel.add(postBtn);
        buttonPanel.add(cancelBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        postBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePost();
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
    
    private void handlePost() {
        String content = contentArea.getText().trim();
        
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some content!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = postDAO.createPost(content, SessionManager.getCurrentUser().userId);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Post created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create post!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
