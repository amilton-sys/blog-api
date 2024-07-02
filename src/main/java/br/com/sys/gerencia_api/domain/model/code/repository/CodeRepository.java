package br.com.sys.gerencia_api.domain.model.code.repository;

import br.com.sys.gerencia_api.domain.model.code.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Optional<Code> findByCode(int code);
    Optional<Code> findByEmail(String email);
}
