package com.example.CRUD.dto.veiculo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VeiculoDTO {

    private String modelo;
    private Integer ano;
    private String cor;
    private String placa;
    private Integer fkuser;
}
