package br.com.sys.gerencia_api.domain.model.post.dto;

import br.com.sys.gerencia_api.domain.model.post.Post;

public record ResponseFeedItem(long postId, String content, String username) {
    public ResponseFeedItem(Post post) {
       this(post.getPostId(),post.getContent(),post.getAuthorId().getUsername());
    }
}
