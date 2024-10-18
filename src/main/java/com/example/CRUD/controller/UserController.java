package com.example.CRUD.controller;
import com.example.CRUD.dto.user.RegisterRequestDTO;
import com.example.CRUD.dto.user.UserDTO;
import com.example.CRUD.dto.user.UserDTOResponse;
import com.example.CRUD.dto.user.UserMapper;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> listar() {
        List<UserEntity> user = userService.listarCliente();

        if(user.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> buscarPorIndice (@PathVariable int id) {
       return ResponseEntity.ok(userService.userPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> atualizar(@PathVariable Integer id, @RequestBody UserEntity userEntity){
        if(userRepository.existsById(id)) {
            userEntity.setId(id);
            return ResponseEntity.status(200).body(userRepository.save(userEntity));
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/inativar-cliente/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        if(userRepository.existsById(id)) {
            userService.inativarCliente(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/ordernar")
    public ResponseEntity<List<UserDTOResponse>> ordernar () {

        List<UserEntity> usuarios = userService.ordernar();

        return usuarios.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(usuarios
                        .stream().map(UserMapper::toDTO).toList());

    }

    @GetMapping("/ordernar")
    public ResponseEntity<List<UserDTOResponse>> ordernar (
            @RequestBody List<RegisterRequestDTO> users
    ) {
        List<UserEntity> usuarios = userService.ordernar();

        return usuarios.isEmpty() ?
            ResponseEntity.noContent().build() :
            ResponseEntity.ok(usuarios
                    .stream().map(UserMapper::toDTO).toList());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<UserDTOResponse>> pesquisar(
            @RequestParam String nome
    ){

        List<UserEntity> usuarios = userService.userPorNome(nome);

        return usuarios.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(usuarios
                        .stream().map(UserMapper::toDTO).toList());

    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<UserDTOResponse>> pesquisar(
            @RequestParam String nome,
            @RequestBody List<RegisterRequestDTO> users
    ){

        List<UserEntity> usuarios = userService.userPorNome(nome, users);

        return usuarios.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(usuarios
                        .stream().map(UserMapper::toDTO).toList());

    }
}
