package com.example.CRUD.entity;

import com.example.CRUD.Pedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "produtos")
@Getter
@Setter
public class ProdutoEntity implements Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    private Integer qtdPedido;
    private Double precoUnitario;
    private String fornecedor;
    private LocalDate dataAtualizcao;
    private Long qtdProdutoEstoque;

    @Override
    public Double calcularPedido() {
        return qtdPedido * precoUnitario;
    }
}
