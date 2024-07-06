package br.com.sys.gerencia_api.service.post;

import br.com.sys.gerencia_api.domain.model.post.Post;
import br.com.sys.gerencia_api.domain.model.post.dto.RequestPost;
import br.com.sys.gerencia_api.domain.model.post.dto.ResponseFeed;
import br.com.sys.gerencia_api.domain.model.post.dto.ResponseFeedItem;
import br.com.sys.gerencia_api.domain.model.post.dto.ResponseFeedItemDetail;
import br.com.sys.gerencia_api.domain.model.post.repository.PostRepository;
import br.com.sys.gerencia_api.domain.model.user.Role;
import br.com.sys.gerencia_api.service.exception.PostNotFoundException;
import br.com.sys.gerencia_api.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional
    public void createPost(RequestPost dto, JwtAuthenticationToken token) {
        var user = userService.findById(UUID.fromString(token.getName()));
        var post = new Post();
        post.setTitle(dto.title());
        post.setDescription(dto.description());
        post.post(user, dto.content());
        if(Objects.nonNull(dto.thumb())){
            post.setThumb(dto.thumb());
        }
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, JwtAuthenticationToken token) {
        var post = findPostById(postId);
        var user = userService.findById(UUID.fromString(token.getName()));
        var isAdmin = user.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (post.getAuthorId().getUserId().equals(UUID.fromString(token.getName())) || isAdmin) {
            postRepository.delete(post);
        } else {
            throw new BadCredentialsException("Access danied.");
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public ResponseFeed feed(Pageable pageable) {
        var posts = postRepository.findAll(pageable)
                .map(ResponseFeedItem::new);
        return new ResponseFeed(posts.getContent());
    }

    public ResponseFeed findMyPosts(JwtAuthenticationToken token, Pageable pageable) {
        var posts = postRepository.findAll(pageable)
                .filter(post -> post.getAuthorId().getUserId().equals(UUID.fromString(token.getName())))
                .map(ResponseFeedItem::new);

        return new ResponseFeed(posts.toList());
    }

    public ResponseFeedItemDetail getPostById(Long id) {
        var posts = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        return new ResponseFeedItemDetail(posts);
    }
}
