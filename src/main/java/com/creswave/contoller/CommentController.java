package com.creswave.contoller;

import com.creswave.model.Comment;
import com.creswave.model.Post;
import com.creswave.payload.CommentRequestDTO;
import com.creswave.payload.PostRequestDTO;
import com.creswave.service.comment.CommentService;
import com.creswave.service.post.PostService;
import com.creswave.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public CommentController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createComment(@Validated @RequestBody CommentRequestDTO commentRequest, Authentication authentication) {

        try {
            // Check if the comment already exists (based on content, assuming content is unique)
            Optional<Comment> existingComment = commentService.getCommentByContent(commentRequest.getContent());
            if (existingComment.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Comment with the same content already exists.");
            }
            // Get the authenticated user's username
            String username = authentication.getName();

            // Create a new comment
            Comment comment = new Comment();
            comment.setContent(commentRequest.getContent());
            // Retrieve the associated post
            Optional<Post> optionalPost = postService.getPostById(commentRequest.getPostId());
            if (!optionalPost.isPresent()) {
                return ResponseEntity.badRequest().body("Post with ID " + commentRequest.getPostId() + " does not exist.");
            }
            Post post = optionalPost.get();
            comment.setPost(post);
            // Set other attributes and create the comment
            comment.setCreatedAt(new Date());
            commentService.createComment(comment, username);

            return ResponseEntity.ok("Comment created successfully.");
        } catch (Exception e) {
            // Handle specific exceptions if necessary
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create comment.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@Validated @PathVariable("id") Long id, Authentication authentication, @RequestBody CommentRequestDTO commentRequest) {
        // Retrieve the comment by its ID
        Optional<Comment> commentOptional = commentService.getCommentById(id);

        // Check if the comment exists
        if (!commentOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Return 404 if the comment does not exist
        }

        // Retrieve the existing comment object
        Comment existingComment = commentOptional.get();

        // Update the content of the existing comment with the content from the request DTO
        existingComment.setContent(commentRequest.getContent());

        // Retrieve the post associated with the comment from the request DTO
        Optional<Post> optionalPost = postService.getPostById(commentRequest.getPostId());

        // Check if the associated post exists
        if (!optionalPost.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post with ID " + commentRequest.getPostId() + " does not exist.");
        }
        // Optionally, you can validate other attributes of the commentRequest
        // Set the associated post for the existing comment
        existingComment.setPost(optionalPost.get());
        existingComment.setUpdatedAt(new Date());

        // Save the updated comment
        commentService.updateComment(existingComment);

        // Return success response
        return ResponseEntity.ok("Comment updated successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Comment> existingOptional = commentService.getCommentById(id);
        if (existingOptional.isPresent()) {
            return new ResponseEntity<>(existingOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found..");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            if (!commentService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            commentService.deleteById(id);
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (Exception e) {
            // Handle specific exceptions if necessary
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete comment");
        }
    }

}
