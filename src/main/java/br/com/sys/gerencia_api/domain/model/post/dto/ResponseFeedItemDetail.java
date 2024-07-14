package br.com.sys.gerencia_api.domain.model.post.dto;

import br.com.sys.gerencia_api.domain.model.post.Post;

import java.time.Instant;

public record ResponseFeedItemDetail(long postId, String title, String description, String thumb, String content,
                                     Instant creationTimestamp) {
    public ResponseFeedItemDetail(Post post) {
        this(post.getPostId(), post.getTitle(), post.getDescription(), post.getThumb(), post.getContent(), post.getCreationTimestamp());
    }
}
