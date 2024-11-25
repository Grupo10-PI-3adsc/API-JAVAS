package com.example.CRUD.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioMudarSenhaDto {
    private String email;
    private String senha;
}
