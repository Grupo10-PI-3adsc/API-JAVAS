package com.example.CRUD.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class ProdutoDTO {

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
}
