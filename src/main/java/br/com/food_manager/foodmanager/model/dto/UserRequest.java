package br.com.food_manager.foodmanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import br.com.food_manager.foodmanager.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação de um novo usuário")
public record UserRequest(
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ter formato válido")
        String email,

        @Schema(description = "Nome de login do usuário", example = "joaosilva")
        @NotBlank(message = "Login é obrigatório")
        @Size(min = 3, max = 50, message = "Login deve ter entre 3 e 50 caracteres")
        String login,

        @Schema(description = "Senha do usuário", example = "senha123")
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
        String password,

        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123, Centro")
        @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
        String address,
        
        @Schema(description = "Tipo do usuário", example = "CUSTOMER")
        UserType userType
) {}
