package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;
    private Date dataPedido;
    private Double total;
    private String status;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "Id")
    private UserEntity fkUsuario;
}
