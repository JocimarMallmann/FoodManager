package br.com.food_manager.foodmanager.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Resposta de erro padronizada para todas as exceções da API")
public record ErrorResponse(
        @Schema(description = "Mensagem de erro descritiva", example = "Exemplo de mensagem de erro")
        String message,
        
        @Schema(description = "Código de status HTTP", example = "400")
        int status,
        
        @Schema(description = "Descrição textual do status HTTP", example = "Not Found")
        String error,
        
        @Schema(description = "Data e hora em que o erro ocorreu", example = "2025-08-07 14:30:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        
        @Schema(description = "Caminho da requisição que gerou o erro", example = "/api/user/999")
        String path,
        
        @Schema(description = "Lista de detalhes adicionais do erro (usado principalmente para erros de validação)", 
                example = "[\"Nome é obrigatório\", \"Email deve ter formato válido\"]")
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
