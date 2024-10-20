package com.example.CRUD.entity;

import com.example.CRUD.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="maoDeobra")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaoDeObrEntity implements Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cod;
    private String nome;
    private String descricao;
    private String categoria;
    private Double custoProduto;
    private Double precoMaoDeObra;
    private String responsavel;
    private LocalDate horaEstimada;
    private LocalDate dataInicio;
    private String status;
    private Integer fkUser;

    @ManyToOne
    @JoinColumn(name = "fkVeiculo", referencedColumnName = "Id")
    private VeiculoEntity fkVeiculo;

    @Override
    public Double calcularPedido() {
        return precoMaoDeObra + custoProduto;
    }
}
