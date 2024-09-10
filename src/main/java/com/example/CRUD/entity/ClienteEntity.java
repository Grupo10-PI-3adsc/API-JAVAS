package com.example.CRUD.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

// CRIANDO UMA TABELA
@Entity
@Table(name = "cliente")
@Getter
@Setter
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Column(name = "Endereco")
    private String endereco;

    @Column(name = "Telefone")
    private String telefone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Data_Cadastro")
    private String dataCadastro;

}



//package com.example.projeto_03_http_persistencia;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Musica {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String nome;
//    private String artista;
//
//    @Column(name = "anoLancamento")
//    private Integer anoLancamento;
//
//    public Musica() {
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getArtista() {
//        return artista;
//    }
//
//    public void setArtista(String artista) {
//        this.artista = artista;
//    }
//
//    public Integer getAnoLancamento() {
//        return anoLancamento;
//    }
//
//    public void setAnoLancamento(Integer anoLancamento) {
//        this.anoLancamento = anoLancamento;
//    }
//
//
//}
