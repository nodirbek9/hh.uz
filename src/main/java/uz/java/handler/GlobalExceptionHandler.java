package uz.java.handler;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.GenericRuntimeException;
import uz.java.exception.InvalidDataException;
import uz.java.util.ErrorUtil;
import uz.java.util.Translator;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Translator translator;

    @ExceptionHandler(GenericNotFoundException.class)
    public ResponseEntity<Object> handleGenericNotFoundException(GenericNotFoundException ex) {
        log.error("GenericNotFoundException on: {}", ErrorUtil.getStacktrace(ex));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(ex.getMessage()))),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException on: {}", ErrorUtil.getStacktrace(ex));
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(ex.getMessage()))),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex) {
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(ex.getMessage()))),
        HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError(final MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException on: {}", ErrorUtil.getStacktrace(ex));
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError ->
                {
                    if (!translator.toLocale(fieldError.getDefaultMessage()).equals(fieldError.getDefaultMessage())) {
                        return Objects.requireNonNull(translator.toLocale(fieldError.getDefaultMessage()));
                    } else {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }

                }).toList();
        return new ResponseEntity<>(Map.of("message", errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericRuntimeException.class)
    public ResponseEntity<Object> handleGenericRuntimeException(GenericRuntimeException ex) {
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(ex.getMessage()))),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(Map.of("message", List.of(translator.toLocale(ex.getMessage()))),
                HttpStatus.BAD_REQUEST
        );
    }
}
