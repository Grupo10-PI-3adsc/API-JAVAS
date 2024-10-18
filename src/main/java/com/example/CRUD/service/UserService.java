package com.example.CRUD.service;
import com.example.CRUD.dto.user.LoginRequestDTO;
import com.example.CRUD.dto.user.RegisterRequestDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.permissionSets;
import com.example.CRUD.repository.UserRepository;
//import io.jsonwebtoken.security.Keys;
import com.example.CRUD.security.securityToken.TokenService;
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

    public UserEntity save(UserEntity user, Integer enderecoId) {

        Optional<UserEntity> userEntityOptional =  userRepository.findByEmail(user.getEmail());

        if (userEntityOptional.isPresent()){
            throw (new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado!"));
        }

        EnderecoEntity endereco = enderecoService.buscarPorId(enderecoId);
        user.setFkEndereco(endereco);

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


    public UserEntity inativarCliente(int id) {
        Boolean ativo = false;
        UserEntity user = this.userPorId(id);
        EnderecoEntity endereco = enderecoService.buscarPorId(id);

        endereco.setIsActive(ativo);
        user.setIsActive(ativo);
        userRepository.save(user);
        endereco.setIsActive(ativo);
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

    public List<UserEntity> ordernar() {
        List<UserEntity> users = userRepository.findAll();
        return particiona(users, 0, users.size());
    }
    public List<UserEntity> ordernar(List<UserEntity> users) {
        return particiona(users, 0, users.size());
    }

    public static List<UserEntity> particiona(List<UserEntity> v, int indInicio, int indFim){
        int i = indInicio;
        int j = indFim;

        if (v.get((indInicio + indFim) /2) != null  && v.get(i) != null){

            String pivo = v.get((indInicio + indFim) /2).getNome();

            while (i <= j ){
                while (i < indFim && (v.get(i).getNome().compareTo(pivo) < 0)){
                    i++;
                }
                while (j > indInicio && (v.get(i).getNome().compareTo(pivo) > 0)){
                    j--;
                }
                if (i<=j){
                    var aux = v.get(i);
                    v.set(i, v.get(j));
                    v.set(j, aux);
                    i++;
                    j--;
                }
            }

            if (indInicio < j){
                particiona(v, indInicio, j);
            }
            if (i<indFim){
                particiona(v, i, indFim);
            }
        }

        return v;

    }

    public List<UserEntity> userPorNome(String nome, List<RegisterRequestDTO> users) {

    }
}
