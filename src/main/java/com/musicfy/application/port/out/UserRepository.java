package com.musicfy.application.port.out;

import com.musicfy.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);
}
