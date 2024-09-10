package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="maoDeobra")
@Getter
@Setter
public class MaoDeObrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    private Integer qtdEstoque;
    private Double precoUnitario;
    private String fornecedor;
    private String localizacao;
    private LocalDate dataAtualizcao;

}

//package com.example.CRUD.entity;
//
//import jakarta.persistence.*;
//        import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "maoDeobra")
//@Getter
//@Setter
//public class MaoDeObraEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String nome;
//    private String descricao;
//    private String categoria;
//    private Integer qtdEstoque;
//    private Double precoUnitario;
//    private String fornecedor;
//    private String localizacao;
//    private LocalDate dataAtualizcao;
//}

