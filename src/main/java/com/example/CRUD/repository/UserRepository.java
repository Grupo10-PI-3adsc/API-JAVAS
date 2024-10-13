package com.example.CRUD.repository;

import com.example.CRUD.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface    UserRepository extends JpaRepository<UserEntity, Integer> {

    // Buscar por nome
    List<UserEntity> findByNomeContainingIgnoreCase(String nome);

    Optional<UserEntity> findByEmail(String email);
}
