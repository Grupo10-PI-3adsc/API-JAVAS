package com.example.CRUD.security.dto;

import java.time.LocalDate;

public record RegisterRequestDTO(Integer Id, String name, String email, String password, String cpfCnpj, String role, String telefone) {
}
