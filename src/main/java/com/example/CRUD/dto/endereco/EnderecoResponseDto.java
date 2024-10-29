package com.example.CRUD.dto.endereco;

import com.example.CRUD.permissionSets;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoResponseDto {

    private String cep;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private Boolean isActive;

}
