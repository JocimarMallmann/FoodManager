package br.com.food_manager.foodmanager.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(String message, HttpStatus httpStatus, LocalDateTime time) {
}
