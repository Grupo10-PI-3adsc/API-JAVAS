package com.example.CRUD.dto.produto;

import com.example.CRUD.entity.ProdutoEntity;

import java.time.LocalDate;

public class ProdutoMapper {

    public static ProdutoEntity toEntity(ProdutoDTO dto) {
        if(dto == null) return null;

        return ProdutoEntity
                .builder()
                .nome(dto.getNome())
                .categoria(dto.getCategoria())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .qtdEstoque(dto.getQtdEstoque())
                .fornecedor(dto.getFornecedor())
                .localizacao(dto.getLocalizacao())
                .dataAtualizcao(LocalDate.now())
                .codBarra(dto.getCodBarra())
                .imagemUrl(dto.getImagemUrl())
                .build();
    }

    public static ProdutoResponseDto toDto(ProdutoEntity entity) {
        if(entity == null) return null;

        return ProdutoResponseDto
                .builder()
                .nome(entity.getNome())
                .categoria(entity.getCategoria())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .qtdEstoque(entity.getQtdEstoque())
                .fornecedor(entity.getFornecedor())
                .localizacao(entity.getLocalizacao())
                .dataAtualizcao(entity.getDataAtualizcao())
                .codBarra(entity.getCodBarra())
                .imagemUrl(entity.getImagemUrl())
                .build();
    }

    public static ProdutoPedidoResponseDto toDtoPedido(ProdutoEntity entity) {
        if(entity == null) return null;

        return ProdutoPedidoResponseDto
                .builder()
                .nome(entity.getNome())
                .categoria(entity.getCategoria())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .codBarra(entity.getCodBarra())
                .imagemUrl(entity.getImagemUrl())
                .build();
    }
}
