package br.com.food_manager.foodmanager.service;

import br.com.food_manager.foodmanager.exception.InvalidUserDataException;
import br.com.food_manager.foodmanager.exception.UserAlreadyExistsException;
import br.com.food_manager.foodmanager.exception.UserNotFoundException;
import br.com.food_manager.foodmanager.model.User;

import java.util.List;

/**
 * Interface de serviço para gerenciamento de usuários.
 * Define as operações de CRUD e outras funcionalidades relacionadas aos usuários.
 * 
 * @author FoodManager Team
 * @since 1.0
 */
public interface UserService {

    /**
     * Salva um novo usuário no sistema.
     * 
     * @param user o usuário a ser salvo
     * @return o usuário salvo com ID gerado
     * @throws InvalidUserDataException se o usuário for nulo ou dados inválidos
     * @throws UserAlreadyExistsException se já existir usuário com o mesmo email ou login
     */
    User save(User user);

    /**
     * Busca um usuário pelo seu ID.
     * 
     * @param id o identificador único do usuário
     * @return o usuário encontrado
     * @throws InvalidUserDataException se o ID for nulo
     * @throws UserNotFoundException se o usuário não for encontrado
     */
    User findById(Long id);

    /**
     * Retorna todos os usuários cadastrados no sistema.
     * 
     * @return lista com todos os usuários
     */
    List<User> findAll();

    /**
     * Remove um usuário do sistema pelo seu ID.
     * 
     * @param id o identificador único do usuário a ser removido
     * @throws InvalidUserDataException se o ID for nulo
     * @throws UserNotFoundException se o usuário não for encontrado
     */
    void deleteById(Long id);

    /**
     * Atualiza os dados de um usuário existente.
     * 
     * @param id o identificador único do usuário a ser atualizado
     * @param user objeto contendo os novos dados do usuário
     * @return o usuário atualizado
     * @throws InvalidUserDataException se o ID ou dados do usuário forem nulos
     * @throws UserNotFoundException se o usuário não for encontrado
     * @throws UserAlreadyExistsException se já existir outro usuário com o mesmo email ou login
     */
    User update(Long id, User user);

    /**
     * Busca um usuário pelo seu login.
     * 
     * @param login o login único do usuário
     * @return o usuário encontrado
     * @throws InvalidUserDataException se o login for vazio ou nulo
     * @throws UserNotFoundException se o usuário não for encontrado
     */
    User findByLogin(String login);

    /**
     * Altera a senha de um usuário.
     * 
     * @param userId o identificador único do usuário
     * @param currentPassword a senha atual do usuário
     * @param newPassword a nova senha do usuário
     * @throws InvalidUserDataException se algum parâmetro for nulo/vazio ou senha atual incorreta
     * @throws UserNotFoundException se o usuário não for encontrado
     */
    void changePassword(Long userId, String currentPassword, String newPassword);
}
