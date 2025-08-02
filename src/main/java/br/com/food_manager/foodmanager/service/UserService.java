package br.com.food_manager.foodmanager.service;

import br.com.food_manager.foodmanager.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    User update(Long id, User user);
    User findByLogin(String login);

}
