package com.example.CRUD.security.controller;

import com.example.CRUD.security.dto.LoginRequestDTO;
import com.example.CRUD.security.dto.RegisterRequestDTO;
import com.example.CRUD.security.dto.ResponseDTO;
import com.example.CRUD.entity.ClienteEntity;
import com.example.CRUD.repository.ClienteRepository;
import com.example.CRUD.security.securityToken.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        ClienteEntity cliente = this.clienteRepository.findByEmail(body.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário invalido"));
        if(passwordEncoder.matches(body.password(),cliente.getSenha())) {
            String token = this.tokenService.generateToken(cliente);
            return ResponseEntity.ok(new ResponseDTO(cliente.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<ClienteEntity> cliente = this.clienteRepository.findByEmail(body.email());

        if (cliente.isEmpty()) {
            ClienteEntity newCliente = new ClienteEntity();
            newCliente.setSenha(passwordEncoder.encode(body.password()));
            newCliente.setEmail(body.email());
            newCliente.setNome(body.name());
            this.clienteRepository.save(newCliente);

            String token = this.tokenService.generateToken(newCliente);
            return ResponseEntity.ok(new ResponseDTO(newCliente.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
