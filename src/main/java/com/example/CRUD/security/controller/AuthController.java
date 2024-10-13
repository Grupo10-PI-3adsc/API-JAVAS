package com.example.CRUD.security.controller;

import com.example.CRUD.dto.user.UserDTO;
import com.example.CRUD.dto.user.UserMapper;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.dto.user.LoginRequestDTO;
import com.example.CRUD.dto.user.RegisterRequestDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.security.securityToken.TokenService;
import com.example.CRUD.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginRequestDTO> login(@RequestBody LoginRequestDTO body) {
        LoginRequestDTO user = userService.login(body);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity register
            (
            @RequestBody RegisterRequestDTO body
    )
    {

        UserEntity usuarioParaSalvar = UserMapper.toEntity(body);

        usuarioParaSalvar.setSenha(passwordEncoder.encode(usuarioParaSalvar.getSenha()));

        UserEntity usuarioSalvo = this.userService.save(usuarioParaSalvar, body.getEnderecoId());

        String token = this.tokenService.generateToken(usuarioSalvo);

        UserDTO userDTO = UserMapper.toDTO(usuarioSalvo, token);

        return ResponseEntity.created(null).body(userDTO);

    }
}
