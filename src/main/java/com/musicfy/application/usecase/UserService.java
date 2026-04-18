package com.musicfy.application.usecase;

import com.musicfy.application.exception.DuplicateEmailException;
import com.musicfy.application.port.in.UserUseCase;
import com.musicfy.application.port.out.UserRepository;
import com.musicfy.domain.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class UserService implements UserUseCase {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return getRequiredUser(id);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(user.getEmail());
        }
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getRequiredUser(id);
        repository.deleteById(id);
    }

    private User getRequiredUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }
}
