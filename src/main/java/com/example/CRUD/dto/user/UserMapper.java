package com.example.CRUD.dto.user;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.security.securityToken.TokenService;

public class UserMapper {

    public static UserDTO toDTO(UserEntity user, String token) {
        if (user == null) return null;

        return UserDTO
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .build();

    }

    public static UserEntity toEntity(RegisterRequestDTO user) {
        if (user == null) return null;

        return UserEntity
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
