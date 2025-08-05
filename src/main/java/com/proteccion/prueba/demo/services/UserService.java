package com.proteccion.prueba.demo.services;

import java.util.List;
import java.util.Optional;

import com.proteccion.prueba.demo.entities.User;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User createUser(User user);

    Optional<User> updateUser(Long id, User user);

    Optional<User> deleteUser(Long id);
}