package com.example.CRUD.repository;

import com.example.CRUD.entity.PedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PedidoRespository extends JpaRepository<PedidosEntity, Integer> {

    @Query("SELECT SUM(p.total) FROM PedidosEntity p WHERE p.status = 'Finalizado'")
    Double somarPedidosFinalizados(@Param("dataInicio") LocalDate dataInicio);

    @Query("SELECT COUNT(p) FROM PedidosEntity p WHERE p.status = 'Finalizado'")
    Integer contarVendasRealizadas(@Param("dataInicio") LocalDate dataInicio);

}
