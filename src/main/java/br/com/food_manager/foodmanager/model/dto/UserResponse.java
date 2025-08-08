package br.com.food_manager.foodmanager.model.dto;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta do usuário")
public record UserResponse(
        @Schema(description = "ID único do usuário", example = "1")
        Long id,

        @Schema(description = "Nome completo do usuário", example = "João Silva")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        String email,

        @Schema(description = "Nome de login do usuário", example = "joaosilva")
        String login,

        @Schema(description = "Data da última atualização", example = "2025-08-07T14:30:00Z")
        Date lastUpdated,

        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123, Centro")
        String address
) {}
