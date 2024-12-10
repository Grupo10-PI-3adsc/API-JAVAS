package com.example.CRUD.dto.pedidos;

import com.example.CRUD.entity.PedidosEntity;

public class PedidosMapper {

    public static PedidosEntity toEntity(PedidosDTO pedidos) {
        if(pedidos == null) return null;

        return PedidosEntity
                .builder()
                .dataPedido(pedidos.getDataPedido())
                .total(pedidos.getTotal())
                .status(pedidos.getStatus())
                .observacoes(pedidos.getObservacoes())
                .build();
    }

    public static PedidosResponseDto toDto(PedidosEntity pedidosEntity) {
        if(pedidosEntity == null) return null;

        PedidosResponseDto.UserResponseDto userDto = null;
        if(pedidosEntity.getFkUsuario() != null) {
            userDto = PedidosResponseDto.UserResponseDto
                    .builder()
                    .id(pedidosEntity.getFkUsuario().getId())
                    .nome(pedidosEntity.getFkUsuario().getNome())
                    .telefone(pedidosEntity.getFkUsuario().getTelefone())
                    .build();
        }

        return PedidosResponseDto
                .builder()
                .dataPedido(pedidosEntity.getDataPedido())
                .total(pedidosEntity.getTotal())
                .status(pedidosEntity.getStatus())
                .observacoes(pedidosEntity.getObservacoes())
                .fkUsuario(userDto)
                .build();
    }
}
