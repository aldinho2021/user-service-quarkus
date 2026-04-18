package com.musicfy.adapters.out.persistence;

import com.musicfy.application.port.out.UserRepository;
import com.musicfy.domain.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepository {

    @Override
    public List<User> findAll() {
        return UserEntity.listAll().stream()
                .map(UserEntity.class::cast)
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(UserEntity.findById(id))
                .map(UserEntity.class::cast)
                .map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        entity.persist();
        return toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        UserEntity.deleteById(id);
    }

    private User toDomain(UserEntity e) {
        User u = new User(e.username, e.email);
        u.setId(e.id);
        return u;
    }

    private UserEntity toEntity(User u) {
        UserEntity e = new UserEntity();
        e.username = u.getUsername();
        e.email = u.getEmail();
        return e;
    }
}
