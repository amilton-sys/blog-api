package br.com.sys.gerencia_api.service.exception;

public class CodeAlreadyInUseException extends RuntimeException {
    public CodeAlreadyInUseException(String message) {
        super(message);
    }
}
