package br.com.food_manager.foodmanager.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(Long id) {
        super("Usuário com ID " + id + " não encontrado");
    }
}
