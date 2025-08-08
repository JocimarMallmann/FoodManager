package br.com.food_manager.foodmanager.controller;

import br.com.food_manager.foodmanager.exception.ErrorResponse;
import br.com.food_manager.foodmanager.model.dto.ChangePasswordRequest;
import br.com.food_manager.foodmanager.model.dto.UserRequest;
import br.com.food_manager.foodmanager.model.dto.UserResponse;
import br.com.food_manager.foodmanager.model.dto.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Controller para gerenciamento de usuários")
public interface UserControllerDoc {

    @Operation(
            summary = "Buscar todos os usuários",
            description = "Retorna uma lista completa de usuários cadastrados no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200", 
                            description = "Lista de usuários retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", 
                            description = "Erro interno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<List<UserResponse>> findAll();

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico baseado no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200", 
                            description = "Usuário encontrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "ID inválido fornecido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", 
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", 
                            description = "Erro interno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> getUser(@PathVariable Long id);

    @Operation(
            summary = "Criar novo usuário",
            description = "Cria um novo usuário no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201", 
                            description = "Usuário criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "Dados inválidos fornecidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409", 
                            description = "Usuário já existe (email/login duplicado)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", 
                            description = "Erro interno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest);

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza parcialmente os dados de um usuário existente",
            responses = {
                    @ApiResponse(
                            responseCode = "200", 
                            description = "Usuário atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "Dados inválidos fornecidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", 
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409", 
                            description = "Conflito - email/login já existe",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", 
                            description = "Erro interno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest);

    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "204", 
                            description = "Usuário deletado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "ID inválido fornecido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", 
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", 
                            description = "Erro interno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteUser(@PathVariable Long id);

    @Operation(
            summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário existente",
            responses = {
                    @ApiResponse(
                            responseCode = "204", 
                            description = "Senha alterada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "Senha atual incorreta ou dados inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", 
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500", 
                            description = "Erro interno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request);
}
