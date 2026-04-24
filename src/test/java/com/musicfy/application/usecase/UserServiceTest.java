package com.musicfy.application.usecase;

import com.musicfy.application.exception.DuplicateEmailException;
import com.musicfy.application.port.out.UserRepository;
import com.musicfy.domain.User;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    private UserRepository stubRepository(boolean emailExists, boolean emailExistsForOther, User savedUser) {
        return new UserRepository() {
            @Override public List<User> findAll() { return List.of(); }
            @Override public Optional<User> findById(Long id) {
                return savedUser != null ? Optional.of(savedUser) : Optional.empty();
            }
            @Override public User save(User user) { return user; }
            @Override public User update(User user) { return user; }
            @Override public void deleteById(Long id) {}
            @Override public boolean existsByEmail(String email) { return emailExists; }
            @Override public boolean existsByEmailAndIdNot(String email, Long id) { return emailExistsForOther; }
        };
    }

    @Test
    void shouldThrowNotFoundWhenUserDoesNotExist() {
        UserService service = new UserService(stubRepository(false, false, null));
        assertThrows(NotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void shouldThrowDuplicateEmailWhenEmailAlreadyExists() {
        UserService service = new UserService(stubRepository(true, false, null));
        assertThrows(DuplicateEmailException.class, () -> service.create(new User("aldo", "aldo@mail.com")));
    }

    @Test
    void shouldThrowNotFoundWhenUpdatingNonExistentUser() {
        UserService service = new UserService(stubRepository(false, false, null));
        assertThrows(NotFoundException.class, () -> service.update(99L, new User("aldo", "aldo@mail.com")));
    }

    @Test
    void shouldThrowDuplicateEmailWhenUpdatingWithExistingEmail() {
        User existing = new User("aldo", "aldo@mail.com");
        existing.setId(1L);
        UserService service = new UserService(stubRepository(false, true, existing));
        assertThrows(DuplicateEmailException.class, () -> service.update(1L, new User("aldo", "otro@mail.com")));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        User existing = new User("aldo", "aldo@mail.com");
        existing.setId(1L);
        UserService service = new UserService(stubRepository(false, false, existing));
        User updated = service.update(1L, new User("aldo2", "aldo2@mail.com"));
        assertEquals("aldo2", updated.getUsername());
        assertEquals("aldo2@mail.com", updated.getEmail());
    }
}
