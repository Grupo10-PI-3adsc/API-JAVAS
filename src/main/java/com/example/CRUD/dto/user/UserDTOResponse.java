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
public class UserDTOResponse {

    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private String senha;
    private permissionSets role;
    private UsuarioEnderecoResponseDto endereco;


    @Data
    @Builder
    public static class UsuarioEnderecoResponseDto {
        private String cep;
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;
        private String ibge;
        private String gia;
        private String ddd;
        private String siafi;
    }
}
