package com.example.CRUD.dto.pedidos;

import com.example.CRUD.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class PedidosDTO {

    private LocalDateTime dataPedido;
    private Double total;
    private String status;
    private String observacoes;
    private Integer fkUsuario;
}
