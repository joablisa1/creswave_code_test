package com.creswave.contoller;

import com.creswave.model.Comment;
import com.creswave.model.Post;
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
@RequestMapping("/api/v1/post")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private  final CommentService commentService;

    public PostController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Post> content;
            Pageable paging = PageRequest.of(page, size, Sort.by("createAt").descending());
            Page<Post> pageCards;

            if (keyword == null || keyword.isBlank()) {
                pageCards = postService.findAll(paging);
            } else {
                pageCards = postService.searchPostsByTitleOrContent(keyword,paging);
            }

            content = pageCards.getContent();
            Map<String, Object> map = new HashMap<>();
            map.put("content", content);
            map.put("currentPage", pageCards.getNumber());
            map.put("pageSize", pageCards.getSize());
            map.put("totalItems", pageCards.getTotalElements());
            map.put("totalPages", pageCards.getTotalPages());

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createPost(@Validated @RequestBody PostRequestDTO postRequest, Authentication authentication) {

        try {
            // Check if a post with the same title already exists
            Optional<Post> existingPost = postService.getPostByTitle(postRequest.getTitle());
            if (existingPost.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Post with the same title already exists.");
            }
            // Get the authenticated user's username
            String username = authentication.getName();
            // Create a new post
            Post post = new Post();
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setCreateAt(new Date());
            // Save the new post
            postService.createPost(post,username);

            return ResponseEntity.ok("Post created successfully.");
        } catch (Exception e) {
            // Handle specific exceptions if necessary
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@Validated @PathVariable("id") Long id, Authentication authentication, @RequestBody PostRequestDTO postRequest) {
        try {
            // Check if the post exists
            Optional<Post> postOptional = postService.getPostById(id);
            if (!postOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
            }

            String username = authentication.getName();
            Post post = postOptional.get();

            // Update post attributes
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setUpdateAt(new Date());
            // Update the post
            postService.createPost(post,username);

            return ResponseEntity.ok("Post updated successfully.");
        } catch (Exception e) {
            // Handle specific exceptions if necessary
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update post.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable("id") Long id) {
        Optional<Post> existingOptional = postService.getPostById(id);
        if (existingOptional.isPresent()) {
            return new ResponseEntity<>(existingOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post  not found..");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            if (!postService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            postService.deleteById(id);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            // Handle specific exceptions if necessary
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete post");
        }
    }
}
