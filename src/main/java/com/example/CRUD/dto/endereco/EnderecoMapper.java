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

    public static EnderecoResponseDto toDto(EnderecoEntity entity) {
        if (entity == null) return null;

        EnderecoResponseDto.UserResponseDto userDto = null;
        if (entity.getBairro() != null) {
            userDto = EnderecoResponseDto.UserResponseDto
                    .builder()
                    .nome(entity.getFkUser().getNome())
                    .telefone(entity.getFkUser().getTelefone())
                    .cpfCnpj(entity.getFkUser().getCpfCnpj())
                    .build();
        }

        return EnderecoResponseDto
                .builder()
                .bairro(entity.getBairro())
                .cep(entity.getCep())
                .complemento(entity.getComplemento())
                .ddd(entity.getDdd())
                .gia(entity.getGia())
                .ibge(entity.getIbge())
                .localidade(entity.getLocalidade())
                .logradouro(entity.getLogradouro())
                .siafi(entity.getSiafi())
                .uf(entity.getUf())
                .fkUser(userDto)
                .build();

    }
}