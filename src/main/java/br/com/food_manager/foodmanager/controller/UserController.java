package br.com.food_manager.foodmanager.controller;

import br.com.food_manager.foodmanager.mapper.UserMapper;
import br.com.food_manager.foodmanager.model.User;
import br.com.food_manager.foodmanager.model.dto.UserRequest;
import br.com.food_manager.foodmanager.model.dto.UserResponse;
import br.com.food_manager.foodmanager.model.dto.UserUpdateRequest;
import br.com.food_manager.foodmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponse> response = userMapper.toResponseList(users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User saved = userService.save(user);
        UserResponse response = userMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {

        User userUpdates = userMapper.toEntityForUpdate(userUpdateRequest);
        User updated = userService.update(id, userUpdates);
        UserResponse response = userMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
