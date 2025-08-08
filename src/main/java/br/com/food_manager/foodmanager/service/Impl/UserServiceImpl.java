package br.com.food_manager.foodmanager.service.Impl;

import br.com.food_manager.foodmanager.exception.InvalidUserDataException;
import br.com.food_manager.foodmanager.exception.UserAlreadyExistsException;
import br.com.food_manager.foodmanager.exception.UserNotFoundException;
import br.com.food_manager.foodmanager.model.User;
import br.com.food_manager.foodmanager.repository.UserRepository;
import br.com.food_manager.foodmanager.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new InvalidUserDataException("Usuário não pode ser nulo");
        }

        validateUserData(user);
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
    public User update(Long id, User userUpdates) {
        if (id == null) {
            throw new InvalidUserDataException("ID do usuário não pode ser nulo");
        }

        if (userUpdates == null) {
            throw new InvalidUserDataException("Dados para atualização não podem ser nulos");
        }

        User existingUser = findById(id);

        updateName(existingUser, userUpdates);
        updateEmail(existingUser, userUpdates, id);
        updateLogin(existingUser, userUpdates, id);
        updateAddress(existingUser, userUpdates);

        existingUser.setLastUpdated(new Date());

        return userRepository.save(existingUser);
    }

    private void updateName(User existing, User updates) {
        if (StringUtils.hasText(updates.getName())) {
            existing.setName(updates.getName());
        }
    }

    private void updateEmail(User existing, User updates, Long id) {
        if (StringUtils.hasText(updates.getEmail())) {
            if (!updates.getEmail().equals(existing.getEmail()) &&
                userRepository.existsByEmailAndIdNot(updates.getEmail(), id)) {
                throw new UserAlreadyExistsException("email", updates.getEmail());
            }
            existing.setEmail(updates.getEmail());
        }
    }

    private void updateLogin(User existing, User updates, Long id) {
        if (StringUtils.hasText(updates.getLogin())) {
            if (!updates.getLogin().equals(existing.getLogin()) &&
                userRepository.existsByLoginAndIdNot(updates.getLogin(), id)) {
                throw new UserAlreadyExistsException("login", updates.getLogin());
            }
            existing.setLogin(updates.getLogin());
        }
    }

    private void updateAddress(User existing, User updates) {
        if (StringUtils.hasText(updates.getAddress())) {
            existing.setAddress(updates.getAddress());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        if (!StringUtils.hasText(login)) {
            throw new InvalidUserDataException("Login não pode ser vazio");
        }

        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("login", login));
    }

    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        if (userId == null) {
            throw new InvalidUserDataException("ID do usuário não pode ser nulo");
        }

        if (!StringUtils.hasText(currentPassword)) {
            throw new InvalidUserDataException("Senha atual é obrigatória");
        }

        if (!StringUtils.hasText(newPassword)) {
            throw new InvalidUserDataException("Nova senha é obrigatória");
        }

        User user = findById(userId);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidUserDataException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastUpdated(new Date());
        userRepository.save(user);
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new InvalidUserDataException("Usuário não pode ser nulo");
        }
        if (StringUtils.hasText(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("email", user.getEmail());
        }

        if (StringUtils.hasText(user.getLogin()) && userRepository.existsByLogin(user.getLogin())) {
            throw new UserAlreadyExistsException("login", user.getLogin());
        }
    }
}
