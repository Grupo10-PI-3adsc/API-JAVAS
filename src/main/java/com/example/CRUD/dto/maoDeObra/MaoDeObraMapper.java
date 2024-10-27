package com.example.CRUD.dto.maoDeObra;

import com.example.CRUD.dto.veiculo.VeiculoDTO;
import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.UserEntity;

public class MaoDeObraMapper {

    public static MaoDeObrEntity toEntity(MaoDeObraDTO maoDeObraDTO){
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
                .fkUser(maoDeObraDTO.getFkUser())
                .build();

    }

    public static MaoDeObraResponseDto toDto(MaoDeObrEntity maoDeObrEntity){
        if (maoDeObrEntity == null) return null;

        MaoDeObraResponseDto.VeiculoResponseDto veiculoResponseDto = null;
        if (maoDeObrEntity.getFkVeiculo() != null) {
            veiculoResponseDto = MaoDeObraResponseDto.VeiculoResponseDto
                    .builder()
                    .placa(maoDeObrEntity.getFkVeiculo().getPlaca())
                    .modelo(maoDeObrEntity.getFkVeiculo().getModelo())
                    .ano(maoDeObrEntity.getFkVeiculo().getAno())
                    .chassi(maoDeObrEntity.getFkVeiculo().getChassi())
                    .build();
        }

        return MaoDeObraResponseDto
                .builder()
                .cod(maoDeObrEntity.getCod())
                .nome(maoDeObrEntity.getNome())
                .descricao(maoDeObrEntity.getDescricao())
                .custoProduto(maoDeObrEntity.getCustoProduto())
                .precoMaoDeObra(maoDeObrEntity.getPrecoMaoDeObra())
                .responsavel(maoDeObrEntity.getResponsavel())
                .horaEstimada(maoDeObrEntity.getHoraEstimada())
                .dataInicio(maoDeObrEntity.getDataInicio())
                .fkUser(maoDeObrEntity.getFkUser())
                .fkVeiculo(veiculoResponseDto)
                .build();

    }
}
