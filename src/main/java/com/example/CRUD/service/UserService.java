package com.example.CRUD.service;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.repository.EnderecoRepository;
//import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public UserEntity save(UserEntity user) {
        if(userRepository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado!");
        }
        user.setId(null);
        return userRepository.save(user);
    }

    public List<UserEntity> listarCliente() {
        return userRepository.findAll();
    }

    public UserEntity userPorId(int id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if(userEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userEntityOptional.get();
    }


    public List<UserEntity> userPorNome(String nome) {
        return userRepository.findByNomeContainingIgnoreCase(nome);
    }

    public UserEntity atualizarCliente(UserEntity user, int id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Cliente não encontrado");
        }
        return userRepository.save(user);
    }


    public UserEntity inativarCliente(int id) {
        Boolean ativo = false;
        Optional<UserEntity> user = userRepository.findById(id);
        Optional<EnderecoEntity> endereco = enderecoRepository.findByFkCliente(id);

        if (user.isPresent()) {
            endereco.ifPresent(enderecoEntity -> enderecoEntity.setIsActive(ativo));
            user.get().setIsActive(ativo);
            userRepository.save(user.get());
            endereco.get().setIsActive(ativo);
            return user.get();
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Cliente não encontrado ou Endereço");

    }

}
