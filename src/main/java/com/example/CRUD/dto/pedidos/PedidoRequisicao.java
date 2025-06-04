package com.example.CRUD.dto.pedidos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PedidoRequisicao {
    private List<Integer> carrinho;
    private Boolean intalacao;
}
