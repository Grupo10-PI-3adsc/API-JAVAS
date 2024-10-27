package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Veiculos")
public class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    private String placa;
    private String modelo;
    private String marca;
    private String cor;
    @Column(name = "ano_fabricacao")
    private Integer ano;
    @Column(name = "numero_chassi")
    private String chassi;

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id", referencedColumnName = "Id") // Adicione nullable = false se não permitir nulos
    private UserEntity fkUser;
}

//ALTER TABLE [dbo].[Veiculos]
//ADD fk_usuario_id INT NOT NULL
//CONSTRAINT fk_usuario_id
//FOREIGN KEY (fk_usuario_id) REFERENCES Usuario(Id);