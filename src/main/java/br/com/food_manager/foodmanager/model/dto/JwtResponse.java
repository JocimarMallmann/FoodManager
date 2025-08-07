package br.com.food_manager.foodmanager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o token JWT após login bem-sucedido")
public record JwtResponse(
        @Schema(description = "Token JWT gerado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,
        @Schema(description = "Tipo do token", example = "Bearer")
        String type,
        @Schema(description = "Nome de usuário autenticado", example = "user@example.com")
        String username
) {}
