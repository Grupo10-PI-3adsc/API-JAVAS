package com.example.CRUD.dto.pedidos;

import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.ProdutoEntity;

import java.util.ArrayList;
import java.util.List;

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

    public static PedidosResponseDto toDto(PedidosEntity pedidosEntity, List<ProdutoEntity> produtoEntities) {
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

        List<PedidosResponseDto.ProdutoResposeDto> produtosDto = new ArrayList<>();
        for(ProdutoEntity p : produtoEntities){
            if(p != null) {
                PedidosResponseDto.ProdutoResposeDto pedido =
                        PedidosResponseDto.ProdutoResposeDto
                        .builder()
                                .preco(p.getPreco())
                                .categoria(p.getCategoria())
                                .codBarra(p.getCodBarra())
                                .descricao(p.getDescricao())
                                .imagemUrl(p.getImagemUrl())
                                .nome(p.getNome())
                        .build();

                produtosDto.add(pedido);
            }
        }

        return PedidosResponseDto
                .builder()
                .id(pedidosEntity.getId_pedido())
                .dataPedido(pedidosEntity.getDataPedido())
                .total(pedidosEntity.getTotal())
                .status(pedidosEntity.getStatus())
                .observacoes(pedidosEntity.getObservacoes())
                .fkUsuario(userDto)
                .produtos(produtosDto)
                .build();
    }
}
