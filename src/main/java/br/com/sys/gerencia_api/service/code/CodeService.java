package br.com.sys.gerencia_api.service.code;

import br.com.sys.gerencia_api.domain.model.code.Code;
import br.com.sys.gerencia_api.domain.model.code.repository.CodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CodeService {
    private final CodeRepository codeRepository;
    private final Random random = new Random();

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Code generateCode(String email) {
        Code code = new Code();
        code.setCode(getRandom());
        code.setEmail(email);
        code.setActive(true);
        return codeRepository.save(code);
    }

    private int getRandom() {
        return this.random.nextInt(100000,999999);
    }

    public List<Code> findAll() {
        return codeRepository.findAll();
    }

    public Optional<Code> findByCode(int code) {
        return codeRepository.findByCode(code);
    }

    public Code findByEmail(String email) {
        return codeRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Code not found"));
    }

    public void deleteCode(Code codeEntity) {
        codeRepository.delete(codeEntity);
    }
}
