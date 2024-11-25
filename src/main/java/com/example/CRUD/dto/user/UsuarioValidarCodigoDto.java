package com.example.CRUD.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioValidarCodigoDto {

    private String email;
    private String codigo_recuperar_senha;

}
