package com.example.CRUD.dto.user;

import com.example.CRUD.permissionSets;
import com.example.CRUD.security.securityToken.TokenService;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private String senha;
    private permissionSets role;

}
