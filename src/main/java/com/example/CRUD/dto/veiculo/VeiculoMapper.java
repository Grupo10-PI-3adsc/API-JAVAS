package com.example.CRUD.dto.veiculo;

import com.example.CRUD.entity.VeiculoEntity;

public class VeiculoMapper {

    public static VeiculoEntity toEntity(VeiculoDTO veiculoDTO) {
        if(veiculoDTO == null) return null;

        return VeiculoEntity
                .builder()
                .modelo(veiculoDTO.getModelo())
                .ano(veiculoDTO.getAno())
                .placa(veiculoDTO.getPlaca())
                .cor(veiculoDTO.getCor())
                .fkuser(veiculoDTO.getFkuser())
                .build();
    }

    public static VeiculoEntity toUpdateEntity(VeiculoEntity veiculoEntity, VeiculoDTO veiculoDTO) {

        veiculoEntity.setAno(veiculoDTO.getAno());
        veiculoEntity.setPlaca(veiculoDTO.getPlaca());
        veiculoEntity.setModelo(veiculoDTO.getModelo());
        veiculoEntity.setFkuser(veiculoDTO.getFkuser());

        return veiculoEntity;

    }
}
