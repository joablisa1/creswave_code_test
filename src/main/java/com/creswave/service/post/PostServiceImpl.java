package com.creswave.service.post;

import com.creswave.model.Post;
import com.creswave.model.User;
import com.creswave.payload.PostRequestDTO;
import com.creswave.repository.PostRepository;
import com.creswave.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostServiceImpl implements  PostService{

   private final PostRepository postRepository;
   private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post createPost(Post post, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        post.setAuthor(user.get());
        return postRepository.save(post);
    }

    @Override
    public Post Update(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public void deleteAll() {
     postRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
     postRepository.deleteById(id);
    }

    @Override
    public Page<Post> findAll(Pageable paging) {
        return postRepository.findAll(paging);
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> searchPostsByTitleOrContent(String keyword, Pageable pageable) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    @Override
    public boolean existsById(Long id) {
        postRepository.existsById(id);
        return false;
    }

    @Override
    public Optional<Post> getPostByTitle(String title) {
        return postRepository.getPostByTitle(title);
    }
}
