package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Pedidos")
public class PedidosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Pedido")
    private Integer id_pedido;
    private LocalDateTime dataPedido;
    private Double total;
    private String status;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "Id")
    private UserEntity fkUsuario;
}
