package com.example.CRUD.dto.user;

import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.permissionSets;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO{
    private String nome;
    private String email;
    private String password;
    private String cpfCnpj;
    private permissionSets role;
    private String telefone;
    private Integer enderecoId;

}
