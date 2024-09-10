package com.example.BackendAPI;

import jakarta.persistence.*;
import lombok.*;

// CRIANDO UMA TABELA
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profissoes")
@Entity
public class ProfissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "SALARIO")
    private Double salario;

    @Column(name = "CARGA_HORARIA")
    private Double cargaHoraria;

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
