package com.sns.ui;

import com.sns.dao.CommentDAO;
import com.sns.dao.LikeDAO;
import com.sns.dao.PostDAO;
import com.sns.model.Comment;
import com.sns.model.Post;
import com.sns.util.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FeedFrame extends JFrame {
    private JPanel feedPanel;
    private JButton createPostBtn;
    private JButton refreshBtn;
    private JButton logoutBtn;
    private PostDAO postDAO;
    private LikeDAO likeDAO;
    private CommentDAO commentDAO;
    
    public FeedFrame() {
        setTitle("SNS - Feed");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        postDAO = new PostDAO();
        likeDAO = new LikeDAO();
        commentDAO = new CommentDAO();
        
        // Top panel for buttons
        JPanel topPanel = new JPanel();
        createPostBtn = new JButton("Create Post");
        refreshBtn = new JButton("Refresh");
        logoutBtn = new JButton("Logout");
        
        topPanel.add(createPostBtn);
        topPanel.add(refreshBtn);
        topPanel.add(logoutBtn);
        
        // Feed panel for posts
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        createPostBtn.addActionListener(e -> new CreatePostFrame());
        refreshBtn.addActionListener(e -> loadFeed());
        logoutBtn.addActionListener(e -> handleLogout());
        
        setVisible(true);
        loadFeed();
    }
    
    private void loadFeed() {
        feedPanel.removeAll();
        List<Post> posts = postDAO.getAllPosts();
        
        for (Post post : posts) {
            JPanel postCard = createPostCard(post);
            feedPanel.add(postCard);
            feedPanel.add(Box.createVerticalStrut(10));
        }
        
        feedPanel.revalidate();
        feedPanel.repaint();
    }
    
    private JPanel createPostCard(Post post) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        card.setMaximumSize(new Dimension(500, 150));
        
        // Post info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("<html><b>" + post.username + "</b></html>"));
        infoPanel.add(new JLabel(post.content));
        infoPanel.add(new JLabel("<html><i>" + post.createdAt + "</i></html>"));
        
        card.add(infoPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        
        // Like button
        post.likeCount = likeDAO.countByPost(post.postId);
        JButton likeBtn = new JButton("Like (" + post.likeCount + ")");
        likeBtn.addActionListener(e -> {
            likeDAO.toggleLike(post.postId, SessionManager.getCurrentUser().userId);
            post.likeCount = likeDAO.countByPost(post.postId);
            likeBtn.setText("Like (" + post.likeCount + ")");
        });
        
        // Comments button
        List<Comment> comments = commentDAO.getByPostId(post.postId);
        post.commentCount = comments.size();
        JButton commentBtn = new JButton("Comments (" + post.commentCount + ")");
        commentBtn.addActionListener(e -> showComments(post, comments));
        
        buttonPanel.add(likeBtn);
        buttonPanel.add(commentBtn);
        
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void showComments(Post post, List<Comment> comments) {
        JDialog commentsDialog = new JDialog(this, "Comments for Post " + post.postId, true);
        commentsDialog.setSize(400, 400);
        commentsDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Comments list
        JPanel commentsList = new JPanel();
        commentsList.setLayout(new BoxLayout(commentsList, BoxLayout.Y_AXIS));
        
        for (Comment c : comments) {
            JLabel commentLabel = new JLabel("<html><b>" + c.username + ":</b> " + c.content + "</html>");
            commentsList.add(commentLabel);
        }
        
        JScrollPane scrollPane = new JScrollPane(commentsList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add comment panel
        JPanel addCommentPanel = new JPanel();
        addCommentPanel.setLayout(new BorderLayout(10, 10));
        
        JTextField commentField = new JTextField();
        JButton addBtn = new JButton("Add Comment");
        
        addBtn.addActionListener(e -> {
            String text = commentField.getText().trim();
            if (!text.isEmpty()) {
                commentDAO.addComment(post.postId, SessionManager.getCurrentUser().userId, text);
                commentField.setText("");
                JOptionPane.showMessageDialog(commentsDialog, "Comment added!");
                commentsDialog.dispose();
            }
        });
        
        addCommentPanel.add(commentField, BorderLayout.CENTER);
        addCommentPanel.add(addBtn, BorderLayout.EAST);
        
        mainPanel.add(addCommentPanel, BorderLayout.SOUTH);
        
        commentsDialog.add(mainPanel);
        commentsDialog.setVisible(true);
    }
    
    private void handleLogout() {
        SessionManager.logout();
        dispose();
        new LoginFrame().setVisible(true);
    }
}
