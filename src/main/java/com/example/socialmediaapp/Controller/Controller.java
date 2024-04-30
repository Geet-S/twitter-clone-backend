package com.example.socialmediaapp.Controller;

import com.example.socialmediaapp.Entity.Comment;
import com.example.socialmediaapp.Entity.Post;
import com.example.socialmediaapp.Entity.User;
import com.example.socialmediaapp.Model.CommentRequest;
import com.example.socialmediaapp.Model.PostRequest;
import com.example.socialmediaapp.Model.UserRequest;
import com.example.socialmediaapp.Service.CommentService;
import com.example.socialmediaapp.Service.PostService;
import com.example.socialmediaapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest loginRequest) {
        // Validate login credentials
        if (userService.isValidUser(loginRequest.getEmail(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "Username/Password Incorrect"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequest signupRequest) {
        if (userService.createUser(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getPassword())) {
            return ResponseEntity.ok("Account Creation Successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("Error", "Forbidden, Account already exists"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserDetails(@RequestParam("userID") int userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Object createPostResponse(Post post, List<Comment> comments) {

        Map<String, Object> postResponse = new HashMap<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        postResponse.put("postID", post.getPostId());
        postResponse.put("postBody", post.getPostBody());
        postResponse.put("date", dateFormatter.format(post.getDate())); // Format date

        List<Map<String, Object>> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            Map<String, Object> commentResponse = new HashMap<>();
            commentResponse.put("commentID", comment.getCommentId());
            commentResponse.put("commentBody", comment.getCommentBody());
            // You can directly access user ID from comment entity
            commentResponse.put("userID", comment.getUserId());
            commentResponse.put("name", userService.getUserById(comment.getUserId()));
            commentResponses.add(commentResponse);
        }
        postResponse.put("comments", commentResponses);
        return postResponse;
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        List<Object> postsResponse = new ArrayList<>();
        for (Post post : allPosts) {
            List<Comment> comments = commentService.getAllCommentsForPost(post.getPostId());
            postsResponse.add(createPostResponse(post, comments));
        }
        return ResponseEntity.ok(postsResponse);
    }


    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        if (userService.isValidUserById(postRequest.getUserID())) {
            postService.createPost(postRequest.getUserID(), postRequest.getPostBody());
            return ResponseEntity.ok("Post created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","User does not exist"));
        }
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostDetails(@RequestParam int postID) {
        Post post = postService.getPostById(postID);
        if (post != null) {
            List<Comment> comments = commentService.getAllCommentsForPost(postID);
            return ResponseEntity.ok(createPostResponse(post, comments));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Post does not exist"));
        }
    }


    @PatchMapping("/post/{postId}")
    public ResponseEntity<?> editPost(@PathVariable int postId, @RequestBody PostRequest postRequest) {
        // Check if the post exists
        if (postService.postExists(postId)) {
            postService.editPost(postId, postRequest.getPostBody());
            return ResponseEntity.ok("Post edited successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Post does not exist"));
        }
    }

    @DeleteMapping("/post/{postID}")
    public ResponseEntity<?> deletePost(@PathVariable int postID) {
        // Check if the post exists
        if (postService.postExists(postID)) {
            postService.deletePost(postID);
            return ResponseEntity.ok("Post deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Post does not exist"));
        }
    }



//    @PostMapping("/comment")
//    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
//        // Check if the post exists
//        if (postService.postExists(commentRequest.getPostID())) {
//            if (userService.isValidUserById(commentRequest.getUserID())) {
//                commentService.createComment(commentRequest.getCommentBody(), commentRequest.getPostID(), commentRequest.getUserID());
//                return ResponseEntity.ok("Comment created successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","User does not exist"));
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Post does not exist"));
//        }
//    }
@PostMapping("/comment")
public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
    // Check if the post exists
    if (postService.postExists(commentRequest.getPostID())) {
        // Check if the user exists
        if (userService.isValidUserById(commentRequest.getUserID())) {
            commentService.createComment(commentRequest.getCommentBody(), commentRequest.getPostID(), commentRequest.getUserID());
            return ResponseEntity.ok(Map.of("message", "Comment created successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "User does not exist"));
        }
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Post does not exist"));
    }
}


    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Comment does not exist"));
        }
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable int commentId, @RequestBody CommentRequest commentRequest) {
        // Check if the comment exists
        if (commentService.commentExists(commentId)) {
            commentService.editComment(commentId, commentRequest.getCommentBody());
            return ResponseEntity.ok("Comment edited successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Comment does not exist"));
        }
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        // Check if the comment exists
        if (commentService.commentExists(commentId)) {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Comment does not exist"));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers(){
    List<User> users = userService.getAllUsers();
    List<Map<String, Object>> userResponses = new ArrayList<>();
    for (User user : users) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("userID", user.getUserID());
        userResponse.put("email", user.getEmail());
        userResponse.put("name", user.getName());
        userResponses.add(userResponse);
    }
    return ResponseEntity.ok(userResponses);
    }
}
