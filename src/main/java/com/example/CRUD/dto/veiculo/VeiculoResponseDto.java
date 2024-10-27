package com.example.CRUD.dto.veiculo;

import com.example.CRUD.dto.user.UserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoResponseDto {

    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;
    private String chassi;
    private UserDto fkuser;

    @Data
    @Builder
    public static class UserDto {
        private Integer id;
        private String nome;
        private String telefone;
    }
}
