package com.creswave.contoller;

import com.creswave.model.Post;
import com.creswave.payload.PostRequestDTO;
import com.creswave.service.comment.CommentService;
import com.creswave.service.post.PostService;
import com.creswave.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Disabled
    @Test
    void testCreatePost() {
        // Arrange
        PostRequestDTO requestDTO = new PostRequestDTO();
        requestDTO.setTitle("Test Title");
        requestDTO.setContent("Test Content");
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("admin");
        when(postService.getPostByTitle(requestDTO.getTitle())).thenReturn(Optional.empty());
        // Act
        ResponseEntity<?> responseEntity = postController.createPost(requestDTO, authentication);
        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
     @Disabled
    @Test
    void testCreatePostWithExistingTitle() {
        // Arrange
        PostRequestDTO requestDTO = new PostRequestDTO();
        requestDTO.setTitle("Existing Title");
        requestDTO.setContent("Test Content");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");

        when(postService.getPostByTitle(requestDTO.getTitle())).thenReturn(Optional.of(new Post()));

        // Act
        ResponseEntity<?> responseEntity = postController.createPost(requestDTO, authentication);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
    @Disabled
    @Test
    void testUpdatePost() {
        // Arrange
        long postId = 1L;

        PostRequestDTO requestDTO = new PostRequestDTO();
        requestDTO.setTitle("Updated Title");
        requestDTO.setContent("Updated Content");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");

        Post existingPost = new Post();
        existingPost.setId(postId);
        when(postService.getPostById(postId)).thenReturn(Optional.of(existingPost));

        // Act
        ResponseEntity<?> responseEntity = postController.updatePost(postId, authentication, requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Disabled
    @Test
    void testUpdateNonExistingPost() {
        // Arrange
        long postId = 1L;

        PostRequestDTO requestDTO = new PostRequestDTO();
        requestDTO.setTitle("Updated Title");
        requestDTO.setContent("Updated Content");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");

        when(postService.getPostById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = postController.updatePost(postId, authentication, requestDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Disabled
    @Test
    void testFindPostById() {
        // Arrange
        long postId = 1L;
        Post post = new Post();
        post.setId(postId);

        when(postService.getPostById(postId)).thenReturn(Optional.of(post));

        // Act
        ResponseEntity<?> responseEntity = postController.findPostById(postId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(post, responseEntity.getBody());
    }
    @Disabled
    @Test
    void testFindNonExistingPostById() {
        // Arrange
        long postId = 1L;

        when(postService.getPostById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = postController.findPostById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Disabled
    @Test
    void testDeletePost() {
        // Arrange
        long postId = 1L;

        when(postService.existsById(postId)).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = postController.deleteById(postId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Disabled
    @Test
    void testDeleteNonExistingPost() {
        // Arrange
        long postId = 1L;

        when(postService.existsById(postId)).thenReturn(false);

        // Act
        ResponseEntity<?> responseEntity = postController.deleteById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}