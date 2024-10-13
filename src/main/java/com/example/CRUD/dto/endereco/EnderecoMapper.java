package com.example.CRUD.dto.endereco;

import com.example.CRUD.dto.user.RegisterRequestDTO;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.entity.UserEntity;
import com.gtbr.domain.Cep;

public class EnderecoMapper {


    public static EnderecoEntity toEntity(Cep cep) {
        if (cep == null) return null;

        return EnderecoEntity
                .builder()
                .bairro(cep.getBairro())
                .cep(cep.getCep())
                .complemento(cep.getComplemento())
                .ddd(cep.getDdd())
                .gia(cep.getGia())
                .ibge(cep.getIbge())
                .localidade(cep.getLocalidade())
                .logradouro(cep.getLogradouro())
                .siafi(cep.getSiafi())
                .uf(cep.getUf())
                .build();
    }
}