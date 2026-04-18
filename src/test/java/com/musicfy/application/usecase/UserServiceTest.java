package com.musicfy.application.usecase;

import com.musicfy.application.exception.DuplicateEmailException;
import com.musicfy.application.port.out.UserRepository;
import com.musicfy.domain.User;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    @Test
    void shouldThrowNotFoundWhenUserDoesNotExist() {
        UserRepository repository = new UserRepository() {
            @Override
            public List<User> findAll() {
                return List.of();
            }

            @Override
            public Optional<User> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public User save(User user) {
                return user;
            }

            @Override
            public void deleteById(Long id) {
            }

            @Override
            public boolean existsByEmail(String email) {
                return false;
            }
        };

        UserService service = new UserService(repository);
        assertThrows(NotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void shouldThrowDuplicateEmailWhenEmailAlreadyExists() {
        UserRepository repository = new UserRepository() {
            @Override
            public List<User> findAll() {
                return List.of();
            }

            @Override
            public Optional<User> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public User save(User user) {
                return user;
            }

            @Override
            public void deleteById(Long id) {
            }

            @Override
            public boolean existsByEmail(String email) {
                return true;
            }
        };

        UserService service = new UserService(repository);
        assertThrows(DuplicateEmailException.class, () -> service.create(new User("aldo", "aldo@mail.com")));
    }
}
