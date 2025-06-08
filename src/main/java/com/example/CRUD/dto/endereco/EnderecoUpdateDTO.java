package com.example.CRUD.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDTO {
    private String cep;
    private String bairro;
    private String localidade;
    private String uf;
}
