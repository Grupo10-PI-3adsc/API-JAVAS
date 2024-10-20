package com.example.CRUD.dto.maoDeObra;

import com.example.CRUD.entity.VeiculoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaoDeObraDTO {

    private Integer cod;
    private String nome;
    private String descricao;
    private String categoria;
    private Double custoProduto;
    private Double precoMaoDeObra;
    private String responsavel;
    private LocalDate horaEstimada;
    private LocalDate dataInicio;
    private Integer fkUser;
    private VeiculoResponseDto fkVeiculo;

    @Data
    @Builder
    public static class VeiculoResponseDto extends VeiculoEntity {
        private String placa;
        private String modelo;
        private Integer ano;
        private Integer fkUser;
    }
}
