package com.example.CRUD.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDashDTO {
    private Integer qtdVendasUltimoMes;
    private Double qtdCaixaUltimoMes;
    private Integer qtdItensEstoque;
}
