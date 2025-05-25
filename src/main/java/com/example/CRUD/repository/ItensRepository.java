package com.example.CRUD.repository;

import com.example.CRUD.entity.ItensEntity;
import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensRepository extends JpaRepository<ItensEntity, Integer> {

    List<ItensEntity> findAllByFkPedido(PedidosEntity pedido);

}
