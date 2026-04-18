package com.musicfy.application.usecase;

import com.musicfy.application.port.in.UserUseCase;
import com.musicfy.application.port.out.UserRepository;
import com.musicfy.domain.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserService implements UserUseCase {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<User> findAll() { return repository.findAll(); }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() ->new RuntimeException("User not found"));}

    @Override
    public User create(User user) {return repository.save(user);}

    @Override
    public void delete(Long id) {repository.deleteById(id);}

}

