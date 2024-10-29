package com.example.CRUD.dto.produto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProdutoResponseDto {

    private String nome;
    private String descricao;
    private String categoria;
    private Integer qtdEstoque;
    private Double preco;
    private String fornecedor;
    private String localizacao;
    private LocalDate dataAtualizcao;
    private String codBarra;
}
