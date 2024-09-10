package com.example.CRUD.repository;

import com.example.CRUD.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {
    List<EnderecoEntity> findAllByFkCliente(Integer fkCliente);
    Optional<EnderecoEntity> findByFkClienteAndCep(Integer fkCliente, String cep);
//    @Query("SELECT e FROM EnderecoEntity e JOIN e.fkCliente c")
//    List<EnderecoEntity> buscaJoinEnderecoCliente();
}
