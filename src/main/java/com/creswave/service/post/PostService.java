package com.creswave.service.post;

import com.creswave.model.Post;
import com.creswave.payload.PostRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    Post createPost(Post post,String username);

    Post Update(Post post);

    Optional<Post> getPostById(Long postId);

    void deleteAll();

    void deleteById(Long id);

    Page<Post> findAll(Pageable paging);

    Page<Post> getAllPosts(Pageable pageable);

    Page<Post> searchPostsByTitleOrContent(String keyword, Pageable pageable);

    boolean existsById(Long id);

    Optional<Post> getPostByTitle(String title);
}
