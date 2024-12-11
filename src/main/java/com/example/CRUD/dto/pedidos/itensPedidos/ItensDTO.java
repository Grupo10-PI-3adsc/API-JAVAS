package com.example.CRUD.dto.pedidos.itensPedidos;

import com.example.CRUD.entity.ProdutoEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItensDTO {

    private Integer quantidade;
    private Integer fkProduto;
    private Integer fkPedido;

}
