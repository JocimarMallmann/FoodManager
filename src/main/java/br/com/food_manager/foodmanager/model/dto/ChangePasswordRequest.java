package br.com.food_manager.foodmanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para alteração de senha do usuário")
public record ChangePasswordRequest(
        @Schema(description = "Senha atual do usuário", example = "senhaAtual123")
        @NotBlank(message = "Senha atual é obrigatória")
        String currentPassword,

        @Schema(description = "Nova senha do usuário", example = "novaSenha456")
        @NotBlank(message = "Nova senha é obrigatória")
        @Size(min = 6, max = 100, message = "Nova senha deve ter entre 6 e 100 caracteres")
        String newPassword
) {}