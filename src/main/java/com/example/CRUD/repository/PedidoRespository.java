package com.example.CRUD.repository;

import com.example.CRUD.entity.PedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRespository extends JpaRepository<PedidosEntity, Integer> {
}
