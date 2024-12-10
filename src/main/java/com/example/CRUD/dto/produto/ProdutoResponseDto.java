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
    private String imagemUrl;

    public ProdutoResponseDto() {
    }


    public ProdutoResponseDto(String nome, String descricao, String categoria, Integer qtdEstoque, Double preco, String fornecedor, String localizacao, LocalDate dataAtualizcao, String codBarra, String imagemUrl) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.fornecedor = fornecedor;
        this.localizacao = localizacao;
        this.dataAtualizcao = dataAtualizcao;
        this.codBarra = codBarra;
        this.imagemUrl = imagemUrl;
    }
}


