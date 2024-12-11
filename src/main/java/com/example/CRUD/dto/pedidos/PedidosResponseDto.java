package com.example.CRUD.dto.pedidos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class PedidosResponseDto {

    private LocalDateTime dataPedido;
    private Double total;
    private String status;
    private String observacoes;
    private UserResponseDto fkUsuario;

    @Data
    @Builder
    public static class UserResponseDto {
        private Integer id;
        private String nome;
        private String telefone;
    }
}
