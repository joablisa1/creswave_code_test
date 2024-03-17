package com.creswave.service.comment;

import com.creswave.model.Comment;
import com.creswave.payload.CommentRequestDTO;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment createComment(Comment comment,String username);

    Optional<Comment> getCommentById(Long commentId);

    void deleteById(Long id);

    List<Comment> findAllComments();

    boolean existsById(Long id);

    Optional<Comment> getCommentByContent(String content);

    Comment updateComment(Comment existingComment);
}
