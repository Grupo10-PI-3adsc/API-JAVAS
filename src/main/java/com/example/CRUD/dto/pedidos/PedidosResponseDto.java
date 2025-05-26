package com.example.CRUD.dto.pedidos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PedidosResponseDto {

    private Integer id;
    private LocalDate dataPedido;
    private Double total;
    private String status;
    private String observacoes;
    private UserResponseDto fkUsuario;
    private List<ProdutoResposeDto> produtos;

    @Data
    @Builder
    public static class UserResponseDto {
        private Integer id;
        private String nome;
        private String telefone;
    }

    @Data
    @Builder
    public static class ProdutoResposeDto {
        private String nome;
        private String descricao;
        private String categoria;
        private Double preco;
        private String codBarra;
        private String imagemUrl;
    }
}
