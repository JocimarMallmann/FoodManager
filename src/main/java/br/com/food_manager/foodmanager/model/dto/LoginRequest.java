package br.com.food_manager.foodmanager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para login do usuário")
public record LoginRequest(
        @Schema(description = "Login do usuário", example = "joao.silva")
        String login,
        @Schema(description = "Senha do usuário", example = "123456")
        String password
) {}
