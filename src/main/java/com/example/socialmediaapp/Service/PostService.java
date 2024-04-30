package com.example.socialmediaapp.Service;

import com.example.socialmediaapp.Entity.Post;
import com.example.socialmediaapp.Repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByPostIdDesc();
    }

    public Post getPostById(int postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public boolean postExists(int postId) {
        return postRepository.existsById(postId);
    }

    public void createPost(int userId, String postBody) {
        Post newPost = new Post();
        newPost.setUserId(userId);
        newPost.setPostBody(postBody);
        postRepository.save(newPost);
    }

    public void editPost(int postId, String newPostBody) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setPostBody(newPostBody);
            postRepository.save(post);
        }
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }
}

