package com.musicfy.application.port.in;

import com.musicfy.domain.User;

import java.util.List;

public interface UserUseCase {
    List<User> findAll();
    User findById(Long id);
    User create(User user);
    void delete(Long id);
}
