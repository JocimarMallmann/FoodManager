package br.com.food_manager.foodmanager.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização de um usuário existente")
public record UserUpdateRequest(
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        @Email(message = "Email deve ter formato válido")
        String email,

        @Schema(description = "Nome de login do usuário", example = "joaosilva")
        @Size(min = 3, max = 50, message = "Login deve ter entre 3 e 50 caracteres")
        String login,

        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123, Centro")
        @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
        String address
) {}