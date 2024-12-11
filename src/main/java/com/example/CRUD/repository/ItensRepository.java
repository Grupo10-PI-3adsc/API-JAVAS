package com.example.CRUD.repository;

import com.example.CRUD.entity.ItensEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensRepository extends JpaRepository<ItensEntity, Integer> {
}
