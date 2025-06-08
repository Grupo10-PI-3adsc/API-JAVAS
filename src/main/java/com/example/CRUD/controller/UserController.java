package com.example.CRUD.controller;
import com.example.CRUD.dto.endereco.EnderecoMapper;
import com.example.CRUD.dto.endereco.EnderecoResponseDto;
import com.example.CRUD.dto.user.*;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.EnderecoService;
import com.example.CRUD.service.UserService;

import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EnderecoService enderecoService;

    @Operation(description = "Mostra os usuários cadastrados")
    @ApiResponses(value = {            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não há usuários cadastrados")
    })
    @GetMapping
    public ResponseEntity<List<UserEntity>> listar() {
        List<UserEntity> user = userService.listarCliente();

        if (user.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(user);  // Corrigido para 200 OK ao retornar lista
    }

    @Operation(description = "Busca um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> buscarPorIndice(@PathVariable int id) {
        UserEntity user = userService.userPorId(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(description = "Atualiza um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTOResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody RegisterUpdateDTO userEntity) {
        EnderecoEntity endereco = null;
        if (userEntity.getEndereco().getId() != null && userEntity.getEndereco().getId() > 0){
            endereco = enderecoService.buscarPorId(userEntity.getEndereco().getId());
        }else if (userEntity.getEndereco().getCep() != null || userEntity.getEndereco().getCep() != ""){
            endereco = enderecoService.save(EnderecoMapper.toEntity(ViaCepClient.findCep(userEntity.getEndereco().getCep())), null);
        }
        if (endereco != null){
            endereco.setBairro(userEntity.getEndereco().getBairro());
            endereco.setId(userEntity.getEndereco().getId());
            endereco.setCep(userEntity.getEndereco().getCep());
            endereco.setUf(userEntity.getEndereco().getUf());
            endereco.setLocalidade(userEntity.getEndereco().getLocalidade());
        }

        UserEntity user = userService.atualizar(UserMapper.toEntity(userEntity), id, endereco);
        return ResponseEntity.status(200).body(UserMapper.toDTOEnd(user));
    }

    @Operation(description = "Inativa um usuário (cliente) pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/inativar/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        userService.inativarCliente(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/ordernar")
    public ResponseEntity<List<UserDTOResponse>> ordernar () {

        List<UserEntity> usuarios = userService.ordernar();

        return usuarios.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(usuarios
                        .stream().map(UserMapper::toDTOEnd).toList());

    }

//    @GetMapping("/ordernar")
//    public ResponseEntity<List<UserDTOResponse>> ordernar (
//            @RequestBody List<RegisterRequestDTO> users
//    ) {
//
//        List<UserEntity> userE = users.stream().map(UserMapper :: toEntity).toList();
//
//        UserEntity[] userEntities = new UserEntity[users.size()];
//
//        for (int i = 0; i < users.size(); i++) {
//            userEntities[i] = userE.get(i);
//        }
//
//        userEntities = userService.ordernar(userEntities);
//
//        List<UserEntity> users2 = new ArrayList<>(Arrays.asList(userEntities));
//
//        return users2.isEmpty() ?
//            ResponseEntity.noContent().build() :
//            ResponseEntity.ok(users2
//                    .stream().map(UserMapper::toDTO).toList());
//    }

    @GetMapping("/pesquisar")
    public ResponseEntity<UserDTO> pesquisar(
            @RequestParam String email
    ){

        int usuarioInd = userService.pesquisaBinaria(email);

        UserEntity usuario = userService.userPorId(usuarioInd);

        return ResponseEntity.ok(UserMapper.toDTO(usuario));

    }

    //TODO: Usuarios não podem acessar esse end point e nem o abaixo
    @GetMapping("/exportar")
    public ResponseEntity<Void> exportar(
            @RequestParam String nome
    ){

        List<UserEntity> usuario = userService.listarCliente();

        userService.exportar(nome, usuario);

        return usuario.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok().build();

    }

    @PostMapping("/importar")
    public ResponseEntity<List<UserDTO>> importar(
                @RequestParam String nomeArquivo
    ){
        List<UserEntity> users = userService.importar(nomeArquivo);
        return ResponseEntity.created(null).body(users.stream().map(UserMapper :: toDTO).toList());
    }


}
