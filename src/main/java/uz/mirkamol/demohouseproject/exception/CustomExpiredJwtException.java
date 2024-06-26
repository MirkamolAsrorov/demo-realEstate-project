package uz.mirkamol.demohouseproject.exception;

import lombok.Getter;

@Getter
public class CustomExpiredJwtException extends RuntimeException {
    public CustomExpiredJwtException(String message) {
        super(message);
    }
}
