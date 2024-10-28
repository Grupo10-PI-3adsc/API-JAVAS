package com.example.CRUD.entity;

import com.example.CRUD.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoEntity implements Pedido{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    @Column(name = "qtd_estoque")
    private Integer qtdEstoque;
    private Double preco;
    private String fornecedor;
    private String localizacao;
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizcao;
    @Column(name = "cod_barra")
    private String codBarra;

    @Override
    public Double calcularPedido() {
        return qtdEstoque * preco;
    }

}
