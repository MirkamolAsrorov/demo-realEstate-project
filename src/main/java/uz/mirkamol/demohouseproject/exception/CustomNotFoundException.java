package uz.mirkamol.demohouseproject.exception;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String message) {
        super(message);
    }

}
