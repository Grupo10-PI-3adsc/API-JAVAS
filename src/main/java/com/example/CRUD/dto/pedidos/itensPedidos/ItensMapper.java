package com.example.CRUD.dto.pedidos.itensPedidos;

import com.example.CRUD.dto.pedidos.PedidosDTO;
import com.example.CRUD.dto.pedidos.PedidosResponseDto;
import com.example.CRUD.entity.ItensEntity;
import com.example.CRUD.entity.PedidosEntity;
import com.example.CRUD.entity.ProdutoEntity;

public class ItensMapper {

    public static ItensEntity toEntity(ItensDTO itensDTO) {
        if(itensDTO == null) return null;

        return ItensEntity
                .builder()
                .quantidadeProdutos(itensDTO.getQuantidade())
                .build();
    }


    public static ItensResponseDto toDto(ItensEntity itensEntity) {
        if(itensEntity == null) return null;

        ItensResponseDto.PedidosResponseDto.fkUsuarioDTOPedido userDTO = null;
        if (itensEntity.getFkPedido().getFkUsuario().getId() != null){
            userDTO = ItensResponseDto.PedidosResponseDto.fkUsuarioDTOPedido
                    .builder()
                    .telefone(itensEntity.getFkPedido().getFkUsuario().getTelefone())
                    .email(itensEntity.getFkPedido().getFkUsuario().getEmail())
                    .id(itensEntity.getFkPedido().getFkUsuario().getId())
                    .nome(itensEntity.getFkPedido().getFkUsuario().getNome())
                    .build();
        }

        ItensResponseDto.ProdutoResponseDto produtoDto = null;
        if(itensEntity.getFkProduto().getId() != null) {
            produtoDto = ItensResponseDto.ProdutoResponseDto
                    .builder()
                    .id(itensEntity.getFkProduto().getId())
                    .nome(itensEntity.getFkProduto().getNome())
                    .categoria(itensEntity.getFkProduto().getCategoria())
                    .qtdEstoque(itensEntity.getFkProduto().getQtdEstoque())
                    .preco(itensEntity.getFkProduto().getPreco())
                    .codBarra(itensEntity.getFkProduto().getCodBarra())
                    .imagemUrl(itensEntity.getFkProduto().getImagemUrl())
                    .build();
        }

        ItensResponseDto.PedidosResponseDto pedidoDto = null;
        if(itensEntity.getFkPedido().getId_pedido() != null) {
            pedidoDto = ItensResponseDto.PedidosResponseDto
                    .builder()
                    .id(itensEntity.getFkPedido().getId_pedido())
                    .dataPedido(itensEntity.getFkPedido().getDataPedido())
                    .total(itensEntity.getFkPedido().getTotal())
                    .status(itensEntity.getFkPedido().getStatus())
                    .observacoes(itensEntity.getFkPedido().getObservacoes())
                    .fkUsuario(userDTO)
                    .build();
        }

        return ItensResponseDto
                .builder()
                .quantidade(itensEntity.getQuantidadeProdutos())
                .produto(produtoDto)
                .pedido(pedidoDto)
                .build();
    }
}
