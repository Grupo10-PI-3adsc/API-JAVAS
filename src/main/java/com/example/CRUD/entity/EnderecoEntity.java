package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_endereco;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
//    @Column(insertable=false, updatable=false)
    private Integer fkCliente;
    @ManyToOne
    @JoinColumn(name = "cliente", referencedColumnName = "idCliente")
    private ClienteEntity cliente; // Associação com ClienteEntity

}
