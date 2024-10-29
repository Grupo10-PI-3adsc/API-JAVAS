package com.example.CRUD.dto.user;

import com.example.CRUD.permissionSets;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTOCsv {
    private String nome;
    private String email;
    private String password;
    private String cpfCnpj;
    private permissionSets role;
    private String telefone;

    public RegisterRequestDTOCsv(String nome, String email, String password, String cpfCnpj, permissionSets role, String telefone) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.cpfCnpj = cpfCnpj;
        this.role = role;
        this.telefone = telefone;
    }
}
