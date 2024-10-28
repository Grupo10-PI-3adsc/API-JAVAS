package com.example.CRUD.service;
import com.example.CRUD.dto.user.LoginRequestDTO;
import com.example.CRUD.dto.user.RegisterRequestDTO;
import com.example.CRUD.dto.user.RegisterRequestDTOCsv;
import com.example.CRUD.dto.user.UserMapper;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.permissionSets;
import com.example.CRUD.repository.EnderecoRepository;
import com.example.CRUD.repository.UserRepository;
//import io.jsonwebtoken.security.Keys;
import com.example.CRUD.security.securityToken.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
//
//    public List<UserEntity> userPorNome(String nome, List<RegisterRequestDTO> users) {
//
//    }

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

    public List<UserEntity> ordernar() {
        List<UserEntity> users = userRepository.findAll();

        UserEntity[] userEntities = new UserEntity[users.size()];

        for (int i = 0; i < users.size(); i++) {
            userEntities[i] = users.get(i);
        }

        userEntities = particiona(userEntities, 0, users.size());

        List<UserEntity> users2 = new ArrayList<>();

        for (UserEntity userAtual : userEntities) {
            users2.add(userAtual);
        }

        return users2;
    }
    public UserEntity[] ordernar(UserEntity[] users) {
        return particiona(users, 0, users.length);
    }

    public UserEntity[] particiona(UserEntity[] v, int indInicio, int indFim){
        int i = indInicio;
        int j = indFim;

        if (v[(indInicio + indFim) /2] != null  && v[i] != null){

            String pivo = v[(indInicio + indFim) /2].getNome();

            while (i <= j ){
                while (i < indFim && (v[i].getNome().compareTo(pivo) < 0)){
                    i++;
                }
                while (j > indInicio && (v[i].getNome().compareTo(pivo) > 0)){
                    j--;
                }
                if (i<=j){
                    var aux = v[i];
                    v[i] = v[j];
                    v[j] = aux;
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


    public UserEntity pesquisaBinaria(String x){
        List<UserEntity> lista = userRepository.findAll();
        UserEntity[] vetor = new UserEntity[lista.size()];

        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = lista.get(i);
        }
        vetor = ordernar(vetor);

        int indInf = 0;
        int indSup = vetor.length - 1;

        while (indInf <= indSup){
            int meio = (indInf + indSup)/2;
            if (vetor[meio] == null){
                continue;
            }

            if (vetor[meio].getNome().equals(x)){
                return vetor[meio];
            } else if(x.compareTo(vetor[meio].getNome()) < 0 ){
                indSup = meio - 1;
            }else {
                indInf = meio + 1;
            }
        }
        return null;
    }


    public void exportar(String nomeArquivo, List<UserEntity> userList) {

        UserEntity[] musicas = new UserEntity[userList.size()];

        for (int i = 0; i < musicas.length; i++) {
            musicas[i] = userList.get(i);
        }

        try (
                OutputStream outputStream = new FileOutputStream("%s.csv".formatted(nomeArquivo));
                BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))

        ) {

            escritor.write("%s;%s;%s;%s;%s;%s;%s;%s\n".formatted(
                    "id", "nome", "Cpf/Cnpj", "Data Cadastro", "Email", "Permissão", "Ativo", "Telefone"));

            for (UserEntity musica : musicas){

                if (musica == null){
                    continue;
                }

                escritor.write("%d;%s;%s;%s;%s;%s;%b;%s\n"
                        .formatted(
                                musica.getId(),
                                musica.getNome(),
                                musica.getCpfCnpj(),
                                musica.getDataCadastro(),
                                musica.getEmail(),
                                musica.getRole(),
                                musica.getIsActive(),
                                musica.getTelefone()));
            }


        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public List<UserEntity> importar(String nomeArquivoI) {


        try {
            InputStream inputStream = new FileInputStream("%s.csv".formatted(nomeArquivoI));

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            Scanner leitor = new Scanner(bufferedReader);

            List<UserEntity> userEntities = new ArrayList<>();

            leitor.useDelimiter("[;\\n]");
            leitor.nextLine();
            while (leitor.hasNextLine()){
                String nome = leitor.next();
                String email = leitor.next();
                String senha = leitor.next();
                String cpfCnpj = leitor.next();
                String telefone = leitor.next();
                permissionSets role = permissionSets.valueOf(leitor.next());
                Integer fkEndereco = leitor.nextInt();

                userEntities.add(
                        this.save(
                                UserMapper.toEntity(
                                        new RegisterRequestDTOCsv(
                                                nome,
                                                email,
                                                senha,
                                                cpfCnpj,
                                                role,
                                                telefone,
                                                fkEndereco)), fkEndereco)
                );
            }

            leitor.close();
            return userEntities;

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
