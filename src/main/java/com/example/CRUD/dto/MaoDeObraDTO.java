package com.example.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaoDeObraDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    private Double CustoProduto;
    private Double precoMaoDeObra;
    private String responsavel;
    private LocalDate horaEstimada;
    private LocalDate dataInicio;
    private Integer fkCliente;
}
