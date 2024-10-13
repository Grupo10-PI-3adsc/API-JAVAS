package com.example.CRUD.entity;

import com.example.CRUD.permissionSets;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

// CRIANDO UMA TABELA
@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Column(name = "Telefone")
    private String telefone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Senha")
    private String senha;

    @Column(name = "Ativo")
    private Boolean isActive = true;

    @Column(name = "Data_Cadastro")
    private LocalDate dataCadastro;

    @Column(name = "Funcao")
    private permissionSets role;

    @ManyToOne
    @JoinColumn(name = "fk_endereco_id", referencedColumnName = "id_endereco")
    private EnderecoEntity fkEndereco;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == permissionSets.SYS_ADM)
            return List.of(
                    new SimpleGrantedAuthority("gerente"),
                    new SimpleGrantedAuthority("func"),
                    new SimpleGrantedAuthority("user"));

        else return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}



