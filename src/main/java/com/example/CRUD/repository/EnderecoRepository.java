package com.example.CRUD.repository;

import com.example.CRUD.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {
    List<EnderecoEntity> findAllByFkCliente(Integer fkCliente);
//    Optional<EnderecoEntity> findByFkClienteAndCep(Integer fkCliente, String cep);
////
//    List<EnderecoEntity> findByCliente();

//    List<EnderecoEntity> findAllByFkClienteAndCep(int id, String cep);

//    List<EnderecoEntity> deleteByCepAndFkCliente(String cep, int id);

    Optional<EnderecoEntity> findByFkCliente(int id);

//    Optional<EnderecoEntity> findAllById(List<EnderecoEntity> listAddress);
//

//    @Query("SELECT e FROM EnderecoEntity e JOIN e.fkCliente c")
//    List<EnderecoEntity> buscaJoinEnderecoCliente();
}
