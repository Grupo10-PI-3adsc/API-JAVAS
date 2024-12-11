package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Servico_Produtos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItensEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID_Servico_Produto")
    private Integer id_servico_produto;

    @Column(name = "Quantidade_Produtos")
    private Integer quantidadeProdutos;

    @ManyToOne
    @JoinColumn(name = "ID_Produto", referencedColumnName = "id_produto")
    private ProdutoEntity fkProduto;

    @ManyToOne
    @JoinColumn(name = "ID_Pedido", referencedColumnName = "id_pedido")
    private PedidosEntity fkPedido;

}
