package uz.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RedisNotSerializableException extends RuntimeException {
    public RedisNotSerializableException(String message) {
        super(message);
    }
}
