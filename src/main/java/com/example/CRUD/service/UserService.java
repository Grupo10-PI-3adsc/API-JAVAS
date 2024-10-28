package com.example.CRUD.service;
import com.example.CRUD.dto.user.LoginRequestDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.permissionSets;
import com.example.CRUD.repository.EnderecoRepository;
import com.example.CRUD.repository.UserRepository;
//import io.jsonwebtoken.security.Keys;
import com.example.CRUD.security.securityToken.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EnderecoService enderecoService;
    private final EnderecoRepository enderecoRepository;

    public UserEntity save(UserEntity user) {

        Optional<UserEntity> userEntityOptional =  userRepository.findByEmail(user.getEmail());

        if (userEntityOptional.isPresent()){
            throw (new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado!"));
        }

        if(user.getRole() == null) {
           user.setRole(permissionSets.USER);
        }

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


    public UserEntity inativarCliente(int userId) {
        Boolean inativar = false;

        // Buscar o usuário pelo ID
        UserEntity user = this.userPorId(userId);
        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + userId);
        }

        // Buscar o endereço associado ao usuário. Supondo que você tenha um método para isso.
        EnderecoEntity endereco = enderecoService.buscarPorId(user.getId());
        if (endereco == null) {
            throw new EntityNotFoundException("Endereço não encontrado para o usuário ID: " + userId);
        }

        // Inativar usuário e endereço
        user.setIsActive(inativar);
        endereco.setIsActive(inativar);

        // Salvar as alterações
        userRepository.save(user);
        enderecoRepository.save(endereco);  // Presumindo que você tenha um método salvar no serviço de endereço

        return user;
    }


    public LoginRequestDTO login(LoginRequestDTO body) {
        UserEntity user = this.userRepository.findByEmail(
                body.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário ou usuario invalido"));

        if(passwordEncoder.matches(body.getPassword(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            body.setToken(token);
            return body;

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário ou usuario invalido");
    }
}
