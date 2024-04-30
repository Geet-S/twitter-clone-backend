package com.example.socialmediaapp.Repository;

import com.example.socialmediaapp.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByPostIdDesc();
}