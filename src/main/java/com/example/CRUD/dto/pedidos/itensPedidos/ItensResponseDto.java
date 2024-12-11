package com.example.CRUD.dto.pedidos.itensPedidos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class ItensResponseDto {

    private Integer quantidade;
    private ProdutoResponseDto produto;
    private PedidosResponseDto pedido;

    @Data
    @Builder
    public static class ProdutoResponseDto {
        private Integer id;
        private String nome;
        private String categoria;
        private Integer qtdEstoque;
        private Double preco;
        private String codBarra;
        private String imagemUrl;
    }


    @Data
    @Builder
    public static class PedidosResponseDto{
        private Integer id;
        private LocalDateTime dataPedido;
        private Double total;
        private String status;
        private String observacoes;
        private fkUsuarioDTOPedido fkUsuario;


        @Data
        @Builder
        public static class fkUsuarioDTOPedido {
            private Integer id;
            private String nome;
            private String telefone;
            private String email;
        }
    }
}

