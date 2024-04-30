package com.example.socialmediaapp.Service;

import com.example.socialmediaapp.Entity.Comment;
import com.example.socialmediaapp.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public void createComment(String commentBody, int postId, int userId) {
        if (postService.postExists(postId) && userService.isValidUserById(userId)) {
            Comment newComment = new Comment();
            newComment.setCommentBody(commentBody);
            newComment.setPostId(postId);
            newComment.setUserId(userId);
            commentRepository.save(newComment);
        }
    }

    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public boolean commentExists(int commentId) {
        return commentRepository.existsById(commentId);
    }

    public void editComment(int commentId, String newCommentBody) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setCommentBody(newCommentBody);
            commentRepository.save(comment);
        }
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getAllCommentsForPost(int postId) {
        return commentRepository.findAllByPostIdOrderByCommentIdDesc(postId);
    }
}

