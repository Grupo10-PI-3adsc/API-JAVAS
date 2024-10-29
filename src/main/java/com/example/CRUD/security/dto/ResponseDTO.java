package com.example.CRUD.security.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
public class ResponseDTO{

    private Integer Id;
    private String nome;
    private String cpfCnpj;
    private String endereco;
    private String telefone;
    private String email;
    private String senha;
    private Boolean isActive;
    private LocalDate dataCadastro;
    private String role;
    
    public class TokenSerciceDto {
        private String token;
    }


}
