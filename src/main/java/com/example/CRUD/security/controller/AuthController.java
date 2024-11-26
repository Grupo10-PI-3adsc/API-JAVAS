package com.example.CRUD.security.controller;

import com.example.CRUD.dto.user.*;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.security.securityToken.TokenService;
import com.example.CRUD.service.EnderecoService;
import com.example.CRUD.service.UserService;
import jakarta.validation.Valid;
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
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO body) {
        LoginResponseDTO user = userService.login(body);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity register
            (
            @RequestBody @Valid RegisterRequestDTO body
    )
    {

        UserEntity usuarioParaSalvar = UserMapper.toEntity(body);

        usuarioParaSalvar.setSenha(passwordEncoder.encode(usuarioParaSalvar.getSenha()));

        UserEntity usuarioSalvo = this.userService.save(usuarioParaSalvar);

        if( body.getEnderecoId() != null && body.getEnderecoId() >= 0 ){
            usuarioSalvo.setFkEndereco(enderecoService.buscarPorId(body.getEnderecoId()));
        }

        String token = this.tokenService.generateToken(usuarioSalvo);

        UserDTOResponse userDTO = UserMapper.toDTO(usuarioSalvo, token);

        return ResponseEntity.created(null).body(userDTO);

    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperar(@RequestParam String email) {
        userService.enviarCodigoRecuperarSenha(email);
        return ResponseEntity.ok("Email Enviado!");
    }

    @PostMapping("/recuperar-senha/validar-codigo")
    public ResponseEntity<String> validarCodigoRecuperacaoSenha(@RequestBody UsuarioValidarCodigoDto validarCodigoDto) {
        userService.validarCodigoRecuperacaoSenha(validarCodigoDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Código Valido");
    }

    @PatchMapping("/recuperar-senha/nova-senha")
    public ResponseEntity<String> novaSenha(@RequestBody UsuarioMudarSenhaDto mudarSenhaDto) {
        userService.mudarSenha(mudarSenhaDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Senha alterada com sucesso");
    }
}
