package com.example.CRUD.dto.produto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProdutoPedidoResponseDto {

    private String nome;
    private String descricao;
    private String categoria;
    private Double preco;
    private String codBarra;
    private String imagemUrl;

}
