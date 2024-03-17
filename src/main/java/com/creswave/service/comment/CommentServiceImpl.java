package com.creswave.service.comment;

import com.creswave.model.Comment;
import com.creswave.model.Post;
import com.creswave.model.User;
import com.creswave.repository.CommentRepository;
import com.creswave.repository.PostRepository;
import com.creswave.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class CommentServiceImpl  implements  CommentService{

    private final CommentRepository  commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @Override
    public Comment createComment(Comment comment,String username) {
        Optional<User> user = userRepository.findByUsername(username);
        comment.setAuthor(user.get());
//        comments.setCreatedAt(new Date());
//        Optional<Post> postOptional= postRepository.findById(comment.getPost().getId());
//        comments.setPost(postOptional.get());
//        comments.setContent(comment.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void deleteById(Long id) {
     commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public Optional<Comment> getCommentByContent(String content) {
        return  commentRepository.getCommentByContent(content);
    }

    @Override
    public Comment updateComment(Comment existingComment) {
        return  commentRepository.save(existingComment);
    }
}
