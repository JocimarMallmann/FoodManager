package br.com.food_manager.foodmanager.controller;

import br.com.food_manager.foodmanager.model.dto.UserRequest;
import br.com.food_manager.foodmanager.model.dto.UserResponse;
import br.com.food_manager.foodmanager.model.dto.LoginRequest;
import br.com.food_manager.foodmanager.model.dto.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Controller para autenticação e registro de usuários")
public interface AuthControllerDoc {

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201", 
                            description = "Usuário registrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "Dados inválidos fornecidos"
                    ),
                    @ApiResponse(
                            responseCode = "409", 
                            description = "Usuário já existe com este email/login"
                    )
            }
    )
    ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest);

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna um token JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200", 
                            description = "Login realizado com sucesso, token JWT retornado"
                    ),
                    @ApiResponse(
                            responseCode = "401", 
                            description = "Credenciais inválidas"
                    ),
                    @ApiResponse(
                            responseCode = "400", 
                            description = "Dados de login inválidos"
                    )
            }
    )
    ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest);
}
