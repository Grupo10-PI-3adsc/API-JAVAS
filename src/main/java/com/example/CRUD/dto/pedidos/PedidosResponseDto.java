package com.example.CRUD.dto.pedidos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PedidosResponseDto {

    private Date dataPedido;
    private Double total;
    private String status;
    private String observacoes;
    private UserResponseDto fkUsuario;

    @Data
    @Builder
    public class UserResponseDto {
        private Integer id;
        private String nome;
        private String telefone;
    }
}
