package br.com.food_manager.foodmanager.service.Impl;

import br.com.food_manager.foodmanager.exception.InvalidUserDataException;
import br.com.food_manager.foodmanager.exception.UserAlreadyExistsException;
import br.com.food_manager.foodmanager.exception.UserNotFoundException;
import br.com.food_manager.foodmanager.model.User;
import br.com.food_manager.foodmanager.repository.UserRepository;
import br.com.food_manager.foodmanager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new InvalidUserDataException("Usuário não pode ser nulo");
        }

        validateUserData(user, null);
        user.setLastUpdated(new Date());

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        if (id == null) {
            throw new InvalidUserDataException("ID do usuário não pode ser nulo");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new InvalidUserDataException("ID do usuário não pode ser nulo");
        }

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {
        if (id == null) {
            throw new InvalidUserDataException("ID do usuário não pode ser nulo");
        }

        if (user == null) {
            throw new InvalidUserDataException("Dados do usuário não podem ser nulos");
        }

        // Verifica se o usuário existe
        findById(id);

        validateUserData(user, id);

        user.setId(id);
        user.setLastUpdated(new Date());

        return userRepository.save(user);
    }

    @Override
    public User partialUpdate(Long id, User userUpdates) {
        if (id == null) {
            throw new InvalidUserDataException("ID do usuário não pode ser nulo");
        }

        if (userUpdates == null) {
            throw new InvalidUserDataException("Dados para atualização não podem ser nulos");
        }

        User existingUser = findById(id);

        // Atualiza apenas os campos não nulos/não vazios
        if (StringUtils.hasText(userUpdates.getName())) {
            existingUser.setName(userUpdates.getName());
        }

        if (StringUtils.hasText(userUpdates.getEmail())) {
            // Verifica duplicação apenas se o email está sendo alterado
            if (!userUpdates.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmailAndIdNot(userUpdates.getEmail(), id)) {
                throw new UserAlreadyExistsException("email", userUpdates.getEmail());
            }
            existingUser.setEmail(userUpdates.getEmail());
        }

        if (StringUtils.hasText(userUpdates.getLogin())) {
            // Verifica duplicação apenas se o login está sendo alterado
            if (!userUpdates.getLogin().equals(existingUser.getLogin()) &&
                userRepository.existsByLoginAndIdNot(userUpdates.getLogin(), id)) {
                throw new UserAlreadyExistsException("login", userUpdates.getLogin());
            }
            existingUser.setLogin(userUpdates.getLogin());
        }

        if (StringUtils.hasText(userUpdates.getAddress())) {
            existingUser.setAddress(userUpdates.getAddress());
        }

        existingUser.setLastUpdated(new Date());

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        if (!StringUtils.hasText(login)) {
            throw new InvalidUserDataException("Login não pode ser vazio");
        }

        return userRepository.findByLogin(login).orElse(null);
    }

    @Override
    public void validateUserData(User user, Long excludeId) {
        if (user == null) {
            throw new InvalidUserDataException("Usuário não pode ser nulo");
        }

        // Verificações de duplicação
        if (excludeId == null) {
            // Para criação de novo usuário
            if (StringUtils.hasText(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
                throw new UserAlreadyExistsException("email", user.getEmail());
            }

            if (StringUtils.hasText(user.getLogin()) && userRepository.existsByLogin(user.getLogin())) {
                throw new UserAlreadyExistsException("login", user.getLogin());
            }
        } else {
            // Para atualização de usuário existente
            if (StringUtils.hasText(user.getEmail()) && userRepository.existsByEmailAndIdNot(user.getEmail(), excludeId)) {
                throw new UserAlreadyExistsException("email", user.getEmail());
            }

            if (StringUtils.hasText(user.getLogin()) && userRepository.existsByLoginAndIdNot(user.getLogin(), excludeId)) {
                throw new UserAlreadyExistsException("login", user.getLogin());
            }
        }
    }
}
