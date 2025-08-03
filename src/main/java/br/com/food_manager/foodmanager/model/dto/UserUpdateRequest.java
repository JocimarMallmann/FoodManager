package br.com.food_manager.foodmanager.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String name,

        @Email(message = "Email deve ter formato válido")
        String email,

        @Size(min = 3, max = 50, message = "Login deve ter entre 3 e 50 caracteres")
        String login,

        @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
        String address
) {}