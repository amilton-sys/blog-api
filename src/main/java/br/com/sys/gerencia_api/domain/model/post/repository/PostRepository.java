package br.com.sys.gerencia_api.domain.model.post.repository;

import br.com.sys.gerencia_api.domain.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
