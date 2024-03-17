package com.creswave.contoller;

import com.creswave.model.Comment;
import com.creswave.model.Post;
import com.creswave.payload.CommentRequestDTO;
import com.creswave.service.comment.CommentService;
import com.creswave.service.post.PostService;
import com.creswave.service.user.UserService;
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

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;
    @Mock
    private PostService postService;
    @Mock
    private UserService userService;
    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Disabled
    @Test
    void testCreateComment() {
        CommentRequestDTO requestDTO = new CommentRequestDTO();
        requestDTO.setContent("Test Comment Content");
        requestDTO.setPostId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");

        when(commentService.getCommentByContent(requestDTO.getContent())).thenReturn(Optional.empty());
        when(postService.getPostById(requestDTO.getPostId())).thenReturn(Optional.of(new Post()));

        ResponseEntity<?> responseEntity = commentController.createComment(requestDTO, authentication);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Disabled
    @Test
    void testUpdateComment() {
        CommentRequestDTO requestDTO = new CommentRequestDTO();
        requestDTO.setContent("Updated Test Comment Content");
        requestDTO.setPostId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");

        Comment existingComment = new Comment();
        existingComment.setId(1L);
        when(commentService.getCommentById(existingComment.getId())).thenReturn(Optional.of(existingComment));
        when(postService.getPostById(requestDTO.getPostId())).thenReturn(Optional.of(new Post()));

        ResponseEntity<?> responseEntity = commentController.updateComment(existingComment.getId(), authentication, requestDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}