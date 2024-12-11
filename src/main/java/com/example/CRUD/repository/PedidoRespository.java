package com.example.CRUD.repository;

import com.example.CRUD.entity.PedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRespository extends JpaRepository<PedidosEntity, Integer> {

    @Query("SELECT SUM(p.total) FROM PedidosEntity p WHERE p.status = 'Finalizado'")
    Double somarPedidosFinalizados();

    @Query("SELECT COUNT(p) FROM PedidosEntity p WHERE p.status = 'Finalizado'")
    Integer contarVendasRealizadas();
}
