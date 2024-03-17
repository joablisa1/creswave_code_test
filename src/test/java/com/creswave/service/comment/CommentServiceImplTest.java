package com.creswave.service.comment;

import com.creswave.model.Comment;
import com.creswave.model.Post;
import com.creswave.model.User;
import com.creswave.repository.CommentRepository;
import com.creswave.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Disabled
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void testCreateComment() {
        // Arrange
        String username = "admin";
        User user = new User();
        user.setUsername(username);

        Post post=new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setContent("Test comment");
        comment.setAuthor(user);
        comment.setCreatedAt(new Date());
        comment.setPost(post);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(commentRepository.save(comment)).thenReturn(comment);

        // Act
        Comment createdComment = commentService.createComment(comment, username);

        // Assert
        assertEquals(comment, createdComment);
        assertEquals(user, comment.getAuthor());
        verify(userRepository, times(1)).findByUsername(username);
        verify(commentRepository, times(1)).save(comment);
    }
    @Disabled
    @Test
    void testGetCommentById() {
        // Arrange
        long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        Optional<Comment> retrievedComment = commentService.getCommentById(commentId);

        // Assert
        assertEquals(Optional.of(comment), retrievedComment);
        verify(commentRepository, times(1)).findById(commentId);
    }
    @Disabled
    @Test
    void testDeleteById() {
        // Arrange
        long commentId = 1L;

        // Act
        commentService.deleteById(commentId);

        // Assert
        verify(commentRepository, times(1)).deleteById(commentId);
    }
    @Disabled
    @Test
    void testFindAllComments() {
        // Arrange
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        comments.add(new Comment());

        when(commentRepository.findAll()).thenReturn(comments);

        // Act
        List<Comment> retrievedComments = commentService.findAllComments();

        // Assert
        assertEquals(comments, retrievedComments);
        verify(commentRepository, times(1)).findAll();
    }
    @Disabled
    @Test
    void testExistsById() {
        // Arrange
        long commentId = 1L;

        when(commentRepository.existsById(commentId)).thenReturn(true);

        // Act
        boolean exists = commentService.existsById(commentId);

        // Assert
        assertEquals(true, exists);
        verify(commentRepository, times(1)).existsById(commentId);
    }
    @Disabled
    @Test
    void testGetCommentByContent() {
        // Arrange
        String content = "Test content";
        Comment comment = new Comment();
        comment.setContent(content);

        when(commentRepository.getCommentByContent(content)).thenReturn(Optional.of(comment));

        // Act
        Optional<Comment> retrievedComment = commentService.getCommentByContent(content);

        // Assert
        assertEquals(Optional.of(comment), retrievedComment);
        verify(commentRepository, times(1)).getCommentByContent(content);
    }
    @Disabled
    @Test
    void testUpdateComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setContent("Old content");
        when(commentRepository.save(comment)).thenReturn(comment);
        // Act
        Comment updatedComment = commentService.updateComment(comment);
        // Assert
        assertEquals(comment, updatedComment);
        verify(commentRepository, times(1)).save(comment);
    }
}