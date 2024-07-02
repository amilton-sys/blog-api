package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.domain.model.post.dto.RequestPost;
import br.com.sys.gerencia_api.domain.model.post.dto.ResponseFeed;
import br.com.sys.gerencia_api.service.post.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("feed")
    public ResponseEntity<ResponseFeed> feed(@PageableDefault(sort = "creationTimestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.feed(pageable));
    }

    @GetMapping("myFeed")
    public ResponseEntity<ResponseFeed> feedById(@PageableDefault(sort = "creationTimestamp", direction = Sort.Direction.DESC) Pageable pageable, JwtAuthenticationToken token) {
        return ResponseEntity.ok(postService.findMyPosts(token, pageable));
    }

    @PostMapping("post")
    public ResponseEntity<Void> createPost(@RequestBody @Valid RequestPost dto, JwtAuthenticationToken token) {
        postService.createPost(dto, token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, JwtAuthenticationToken token) {
        postService.deletePost(postId, token);
        return ResponseEntity.ok().build();
    }
}
