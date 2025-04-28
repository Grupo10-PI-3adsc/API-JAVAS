package com.example.CRUD.dto.user;

import com.example.CRUD.permissionSets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String nome;
    private String email;
    private String password;
    private String cpfCnpj;
    private String telefone;
    private String token;
    private UsuarioEnderecoResponseDto endereco;


    @Data
    @Builder
    public static class UsuarioEnderecoResponseDto {
        private String cep;
        private String bairro;
        private String localidade;
        private String uf;
    }
}