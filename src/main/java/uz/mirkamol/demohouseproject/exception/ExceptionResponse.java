package uz.mirkamol.demohouseproject.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponse {
    private Date timestamp;
    private Integer statusCode;
    private String message;
}
