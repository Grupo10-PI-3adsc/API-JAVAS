package com.example.CRUD.dto.user;

import com.example.CRUD.permissionSets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RegisterUpdateDTO {
    private String nome;
    private String email;
    private String cpfCnpj;
    private String telefone;
    private EnderecoUpdateDTO endereco;

    @Data
    @Builder
    public static class EnderecoUpdateDTO {
        private Integer id;
        private String cep;
        private String bairro;
        private String localidade;
        private String uf;
    }

}
