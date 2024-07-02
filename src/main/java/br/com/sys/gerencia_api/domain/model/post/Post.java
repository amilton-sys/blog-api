package br.com.sys.gerencia_api.domain.model.post;

import br.com.sys.gerencia_api.domain.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String content;
    @ManyToOne
    @JoinColumn(
            name = "author_id"
    )
    private User authorId;
    @CreationTimestamp
    private Instant creationTimestamp;

    public void post(User user, String content) {
        this.authorId = user;
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthorId() {
        return authorId;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }
}
