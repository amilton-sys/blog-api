package br.com.sys.gerencia_api.domain.model.post.dto;

import br.com.sys.gerencia_api.domain.model.post.Post;

import java.time.Instant;

public record ResponseFeedItem(long postId, String title, String description,String thumb,Instant creationTimestamp) {
    public ResponseFeedItem(Post post) {
       this(post.getPostId(),post.getTitle(),post.getDescription(),post.getThumb(), post.getCreationTimestamp());
    }
}
