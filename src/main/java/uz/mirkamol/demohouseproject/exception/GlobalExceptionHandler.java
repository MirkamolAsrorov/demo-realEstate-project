package uz.mirkamol.demohouseproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static java.util.Objects.requireNonNull;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

                    exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    exceptionResponse.setMessage(requireNonNull(
                            ex.getFieldError()).getDefaultMessage());
                    exceptionResponse.setTimestamp(new Date());
                }
        );

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(
            CustomNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        exceptionResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setTimestamp(new Date());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(CustomExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleTokenExpiredException(
            CustomExpiredJwtException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        exceptionResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setTimestamp(new Date());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }
}
