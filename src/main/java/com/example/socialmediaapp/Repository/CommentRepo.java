package com.example.socialmediaapp.Repository;

import com.example.socialmediaapp.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPostIdOrderByCommentIdDesc(int postId);

}
