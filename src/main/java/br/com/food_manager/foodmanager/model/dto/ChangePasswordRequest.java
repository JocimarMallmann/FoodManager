package br.com.food_manager.foodmanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Senha atual é obrigatória")
        String currentPassword,

        @NotBlank(message = "Nova senha é obrigatória")
        @Size(min = 6, max = 100, message = "Nova senha deve ter entre 6 e 100 caracteres")
        String newPassword
) {}