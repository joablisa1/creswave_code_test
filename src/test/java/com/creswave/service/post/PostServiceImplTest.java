package com.creswave.service.post;

import com.creswave.model.Post;
import com.creswave.model.User;
import com.creswave.repository.PostRepository;
import com.creswave.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Disabled
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Disabled
    @Test
    void testCreatePost() {
        // Arrange
        String username = "admin";
        User user = new User();
        user.setUsername(username);

        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(postRepository.save(post)).thenReturn(post);

        // Act
        Post createdPost = postService.createPost(post, username);

        // Assert
        assertEquals(post, createdPost);
        assertEquals(user, post.getAuthor());
        verify(userRepository, times(1)).findByUsername(username);
        verify(postRepository, times(1)).save(post);
    }
    @Disabled
    @Test
    void testUpdatePost() {
        // Arrange
        Post post = new Post();
        post.setTitle("Old Title");
        post.setContent("Old Content");

        when(postRepository.save(post)).thenReturn(post);

        // Act
        Post updatedPost = postService.Update(post);

        // Assert
        assertEquals(post, updatedPost);
        verify(postRepository, times(1)).save(post);
    }
}