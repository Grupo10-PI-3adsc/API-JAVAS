package com.example.CRUD.dto.maoDeObra;

import com.example.CRUD.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class MaoDeObraResponseDto {

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
    public static class VeiculoResponseDto {
        private String placa;
        private String modelo;
        private Integer ano;
        private String chassi;
    }
}
