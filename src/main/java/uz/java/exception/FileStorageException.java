package uz.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }
}
