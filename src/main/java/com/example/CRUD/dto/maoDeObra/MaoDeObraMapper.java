package com.example.CRUD.dto.maoDeObra;

import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.UserEntity;

public class MaoDeObraMapper {

    public static MaoDeObrEntity toEntity(MaoDeObraDTO maoDeObraDTO, VeiculoDTO veiculoDTO){
        if (maoDeObraDTO == null) return null;

        return MaoDeObrEntity
                .builder()
                .cod(maoDeObraDTO.getCod())
                .nome(maoDeObraDTO.getNome())
                .descricao(maoDeObraDTO.getDescricao())
                .custoProduto(maoDeObraDTO.getCustoProduto())
                .precoMaoDeObra(maoDeObraDTO.getPrecoMaoDeObra())
                .responsavel(maoDeObraDTO.getResponsavel())
                .horaEstimada(maoDeObraDTO.getHoraEstimada())
                .dataInicio(maoDeObraDTO.getDataInicio())
                .fkVeiculo(MaoDeObraDTO.VeiculoResponseDto
                        .builder()
                        .ano(veiculoDTO.getAno())
                        .placa(veiculoDTO.getPlaca())
                        .modelo(veiculoDTO.getModelo())
                        .fkUser(veiculoDTO.getFkuser())
                        .build())
                .build();

    }
}
