package com.musicfy.adapters.out.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity extends PanacheEntity {

    @Column(nullable = false)
    public String username;

    @Column(nullable = false, unique = true)
    public String email;
}
