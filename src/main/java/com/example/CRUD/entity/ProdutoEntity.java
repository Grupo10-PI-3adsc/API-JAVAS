package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "produtos")
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    private Integer qtdEstoque;
    private Double precoUnitario;
    private String fornecedor;
    private String localizacao;
    private LocalDate dataAtualizcao;
}
