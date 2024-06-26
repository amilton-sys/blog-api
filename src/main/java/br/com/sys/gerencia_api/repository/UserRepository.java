package br.com.sys.gerencia_api.repository;

import br.com.sys.gerencia_api.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}
