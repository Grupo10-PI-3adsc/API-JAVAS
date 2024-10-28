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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(getRole() == permissionSets.SYS_ADM) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_SYS_ADM"),
                    new SimpleGrantedAuthority("ROLE_GERENTE"),

                    new SimpleGrantedAuthority("ROLE_FUNC"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
        if (this.role.equals(permissionSets.GERENTE.getRole())) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_GERENTE"),
                    new SimpleGrantedAuthority("ROLE_FUNC"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
        if (this.role.equals(permissionSets.FUNC.getRole())) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_FUNC"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
      
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

    public boolean isEmpty(){
        return true;
    }
}



