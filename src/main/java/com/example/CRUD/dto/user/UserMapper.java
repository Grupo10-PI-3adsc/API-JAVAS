package com.example.CRUD.security.dto;

import com.example.CRUD.dto.user.UserDTO;
import com.example.CRUD.entity.UserEntity;

public class UserMapper {

    public static UserDTO toDTO(UserEntity user) {
        if (user == null) return null;

        return UserDTO
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                
                .build();
    }
    
}
