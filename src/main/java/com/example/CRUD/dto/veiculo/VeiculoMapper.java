package com.example.CRUD.dto.veiculo;

import com.example.CRUD.dto.maoDeObra.MaoDeObraDTO;
import com.example.CRUD.entity.VeiculoEntity;

public class VeiculoMapper {

    public static VeiculoEntity toEntity(VeiculoDTO veiculoDTO) {
        if (veiculoDTO == null) return null;

        return VeiculoEntity
                .builder()
                .modelo(veiculoDTO.getModelo())
                .ano(veiculoDTO.getAno())
                .placa(veiculoDTO.getPlaca())
                .cor(veiculoDTO.getCor())
                .marca(veiculoDTO.getMarca())
                .chassi(veiculoDTO.getChassi())
                .build();
    }


    public static VeiculoResponseDto toDTO(VeiculoEntity veiculoEntity) {
        if (veiculoEntity == null) return null;

        VeiculoResponseDto.UserDto userDto = null;
        if (veiculoEntity.getFkUser() != null) {
            userDto = VeiculoResponseDto.UserDto
                    .builder()
                    .id(veiculoEntity.getFkUser().getId())
                    .nome(veiculoEntity.getFkUser().getNome())
                    .telefone(veiculoEntity.getFkUser().getTelefone())
                    .build();
        }

            return VeiculoResponseDto
                    .builder()
                    .modelo(veiculoEntity.getModelo())
                    .ano(veiculoEntity.getAno())
                    .placa(veiculoEntity.getPlaca())
                    .cor(veiculoEntity.getCor())
                    .marca(veiculoEntity.getMarca())
                    .chassi(veiculoEntity.getChassi())
                    .fkuser(userDto)
                    .build();

    }
}

