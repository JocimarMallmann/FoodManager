package br.com.food_manager.foodmanager.controller;

import br.com.food_manager.foodmanager.mapper.UserMapper;
import br.com.food_manager.foodmanager.model.User;
import br.com.food_manager.foodmanager.model.UserType;
import br.com.food_manager.foodmanager.model.dto.UserRequest;
import br.com.food_manager.foodmanager.model.dto.UserResponse;
import br.com.food_manager.foodmanager.security.JwtUtils;
import br.com.food_manager.foodmanager.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDoc {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, 
                         AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(UserType.CUSTOMER);
        user.setLastUpdated(new Date());

        User savedUser = userService.save(user);
        UserResponse response = userMapper.toResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", loginRequest.login()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new JwtResponse("", "", "Invalid credentials"));
        }
    }

    @Schema(description = "Dados para login do usuário")
    public record LoginRequest(
            @Schema(description = "Login do usuário (email)", example = "user@example.com")
            String login, 
            @Schema(description = "Senha do usuário", example = "123456")
            String password
    ) {}

    @Schema(description = "Resposta contendo o token JWT após login bem-sucedido")
    public record JwtResponse(
            @Schema(description = "Token JWT gerado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            String token, 
            @Schema(description = "Tipo do token", example = "Bearer")
            String type, 
            @Schema(description = "Nome de usuário autenticado", example = "user@example.com")
            String username
    ) {}

}
