package com.example.CRUD.dto.veiculo;

import com.example.CRUD.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VeiculoDTO {

    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;
    private String chassi;
    private Integer fkUser;

}
