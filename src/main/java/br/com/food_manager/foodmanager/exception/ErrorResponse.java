package br.com.food_manager.foodmanager.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String message,
        int status,
        String error,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        String path,
        List<String> details
) {
    
    public static ErrorResponse of(String message, HttpStatus httpStatus, String path) {
        return new ErrorResponse(
                message,
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                LocalDateTime.now(),
                path,
                null
        );
    }
    
    public static ErrorResponse of(String message, HttpStatus httpStatus, String path, List<String> details) {
        return new ErrorResponse(
                message,
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                LocalDateTime.now(),
                path,
                details
        );
    }
}
