package com.example.CRUD.service;
import com.example.CRUD.dto.user.*;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.exception.JaCadastradoException;
import com.example.CRUD.exception.NaoEncontradoException;
import com.example.CRUD.permissionSets;
import com.example.CRUD.repository.UserRepository;
//import io.jsonwebtoken.security.Keys;
import com.example.CRUD.security.securityToken.TokenService;
import com.example.CRUD.service.JavaMail.JavaMail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserEntity save(UserEntity user) {

        Optional<UserEntity> userEntityOptional =  userRepository.findByEmail(user.getEmail());

        if (userEntityOptional.isPresent()){
            throw (new JaCadastradoException("Usuario Já cadastrado"));
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

        // Inativar usuário e endereço
        user.setIsActive(inativar);

        // Salvar as alterações
        userRepository.save(user);

        return user;
    }


    public LoginResponseDTO login(LoginRequestDTO body) {
        UserEntity user = this.userRepository.findByEmail(
                body.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário ou usuario invalido"));

        if(passwordEncoder.matches(body.getPassword(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return UserMapper.toDTOLogin(user,token);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário ou senha invalido");
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
        return particiona(users, 0, users.length -1);
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

    public int pesquisaBinaria(String x) {
        List<UserEntity> lista = userRepository.findAll();
        UserEntity[] vetor = lista.toArray(new UserEntity[0]);

        quickSortEmail(vetor, 0, vetor.length - 1);

        System.out.println("Array ordenado:");
        for (UserEntity user : vetor) {
            System.out.println(user.getEmail());
        }

        int indInf = 0;
        int indSup = vetor.length - 1;

        while (indInf <= indSup) {
            int meio = (indInf + indSup) / 2;

            if (vetor[meio] == null) {
                break;
            }

            if (vetor[meio].getEmail().equals(x)) {
                return meio;
            } else if (x.compareTo(vetor[meio].getEmail()) < 0) {
                indSup = meio - 1;
            } else {
                indInf = meio + 1;
            }
        }
        return -1;
    }
    public void quickSortEmail(UserEntity[] v, int indInicio, int indFim) {
        if (indInicio < indFim) {
            int pivoIndex = particionaEmail(v, indInicio, indFim);
            quickSortEmail(v, indInicio, pivoIndex - 1);
            quickSortEmail(v, pivoIndex + 1, indFim);
        }
    }

    private int particionaEmail(UserEntity[] v, int indInicio, int indFim) {
        String pivo = v[indFim].getEmail();
        int i = indInicio - 1;

        for (int j = indInicio; j < indFim; j++) {
            if (v[j].getEmail().compareTo(pivo) <= 0) {
                i++;
                UserEntity aux = v[i];
                v[i] = v[j];
                v[j] = aux;
            }
        }

        UserEntity aux = v[i + 1];
        v[i + 1] = v[indFim];
        v[indFim] = aux;

        return i + 1;
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
                String perm = leitor.next();
                String telefone = leitor.next();
                permissionSets role = permissionSets.valueOf(perm);
                userEntities.add(
                        this.save(
                                UserMapper.toEntity(
                                        new RegisterRequestDTOCsv(
                                                nome,
                                                email,
                                                senha,
                                                cpfCnpj,
                                                role,
                                                telefone
                                                )))
                );
            }
            leitor.close();
            return userEntities;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public UserEntity atualizar(UserEntity userEntity, Integer id, EnderecoEntity enderecoEntity) {
        userEntity.setId(id);
        if (!userRepository.existsById(userEntity.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (enderecoEntity != null) userEntity.setFkEndereco(enderecoEntity);
        return userRepository.save(userEntity);
    }

    public void enviarCodigoRecuperarSenha(String email) {
        Optional<UserEntity> usuarioEmail = userRepository.findByEmail(email);

        if (usuarioEmail.isEmpty()){
            throw new NaoEncontradoException(HttpStatus.NOT_FOUND, "Email não cadastrado");
        }

        UserEntity usuario = usuarioEmail.get();

        JavaMail.sendEmail(email, usuario.getNome());
        System.out.println("Esse é o codigo pro email" + JavaMail.getCode());
        usuario.setCodigo_recuperar_senha(JavaMail.getCode());

        LocalDateTime validade = LocalDateTime.now().plusMinutes(10).truncatedTo(ChronoUnit.SECONDS);
        usuario.setValidade_codigo_senha(validade);
        System.out.println("Essa é a validade" + validade);

        userRepository.save(usuario);
    }

    public void validarCodigoRecuperacaoSenha(UsuarioValidarCodigoDto validarSenhaDto) {
        Optional<UserEntity> usuarioEmail = userRepository.findByEmail(validarSenhaDto.getEmail());

        if (usuarioEmail.isEmpty()){
            throw new NaoEncontradoException(HttpStatus.NOT_FOUND, "Email não cadastrado");
        }

        UserEntity usuario = usuarioEmail.get();

        if (usuario.getValidade_codigo_senha().isBefore(LocalDateTime.now())){
            throw new NaoEncontradoException( HttpStatus.BAD_REQUEST, "A validade do codigo expirou");
        }

        if (!validarSenhaDto.getCodigo_recuperar_senha().equals(usuario.getCodigo_recuperar_senha())){
            throw new NaoEncontradoException(HttpStatus.BAD_REQUEST, "Codigo de recuperação está invalido !");
        }
    }

    public void mudarSenha(UsuarioMudarSenhaDto mudarSenhaDto) {
        Optional<UserEntity> usuarioEmail = userRepository.findByEmail(mudarSenhaDto.getEmail());

        if (usuarioEmail.isEmpty()){
            throw new NaoEncontradoException(HttpStatus.NOT_FOUND, "Email não cadastrado");
        }

        UserEntity usuario = usuarioEmail.get();

        if (usuario.getValidade_codigo_senha() == null){
            throw new NaoEncontradoException( HttpStatus.NOT_FOUND, "Nenhum codigo de validação encontrado, solicite um novo");
        }

        if (usuario.getValidade_codigo_senha().isBefore(LocalDateTime.now())){
            throw new NaoEncontradoException(HttpStatus.BAD_REQUEST, "A válidade do código expirou, solicite um novo");
        }

        if(mudarSenhaDto.getSenha() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "senha invalido");
        }

        String hashSenha = passwordEncoder.encode(mudarSenhaDto.getSenha());

        usuario.setSenha(hashSenha);
        usuario.setCodigo_recuperar_senha(null);
        usuario.setValidade_codigo_senha(null);

        userRepository.save(usuario);
    }
}
