package br.com.sys.gerencia_api.domain.model.user.repository;

import br.com.sys.gerencia_api.domain.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
