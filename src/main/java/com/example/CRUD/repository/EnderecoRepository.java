package com.example.CRUD.repository;

import com.example.CRUD.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {

    Optional<EnderecoEntity> findByCep(String cep);
}
