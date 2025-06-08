package com.example.CRUD.dto.user;
import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.security.securityToken.TokenService;

public class UserMapper {

    public static UserDTOResponse toDTO(UserEntity user, String token) {
        if (user == null) return null;

        return UserDTOResponse
                .builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .build();

    }

    public static LoginResponseDTO toDTOLogin(UserEntity user, String token) {
        if (user == null) return null;

        EnderecoEntity endereco = user.getFkEndereco();

        if (endereco != null)

            return LoginResponseDTO
                    .builder()
                    .id(user.getId())
                    .nome(user.getNome())
                    .email(user.getEmail())
                    .cpfCnpj(user.getCpfCnpj())
                    .telefone(user.getTelefone())
                    .password(user.getPassword())
                    .token(token)
                    .role(user.getRole())
                    .endereco(LoginResponseDTO.UsuarioEnderecoResponseDto
                            .builder()
                            .id(endereco.getId())
                            .uf(endereco.getUf())
                            .cep(endereco.getCep())
                            .bairro(endereco.getBairro())
                            .localidade(endereco.getLocalidade())
                            .build())
                    .build();


        return LoginResponseDTO
                .builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .password(user.getPassword())
                .token(token)
                .role(user.getRole())
                .build();

    }


    public static UserDTOResponse toDTOEnd(UserEntity user) {
        if (user == null) return null;

        EnderecoEntity endereco = user.getFkEndereco();

        if (endereco != null)

            return UserDTOResponse
                    .builder()
                    .id(user.getId())
                    .nome(user.getNome())
                    .email(user.getEmail())
                    .cpfCnpj(user.getCpfCnpj())
                    .telefone(user.getTelefone())
                    .senha(user.getPassword())
                    .role(user.getRole())
                    .endereco(
                            UserDTOResponse.UsuarioEnderecoResponseDto
                                    .builder()
                                    .id(endereco.getId())
                                    .bairro(endereco.getBairro())
                                    .cep(endereco.getCep())
                                    .complemento(endereco.getComplemento())
                                    .ddd(endereco.getDdd())
                                    .gia(endereco.getGia())
                                    .ibge(endereco.getIbge())
                                    .uf(endereco.getUf())
                                    .localidade(endereco.getLocalidade())
                                    .siafi(endereco.getSiafi())
                                    .logradouro(endereco.getLogradouro())
                                    .build()
                    )
                    .build();

        return UserDTOResponse
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .endereco(
                        null
                )
                .build();

    }


    public static UserDTO toDTO(UserEntity user) {
        if (user == null) return null;

        EnderecoEntity endereco = user.getFkEndereco();

        return UserDTO
                .builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .build();

    }

    public static UserEntity toEntity(RegisterUpdateDTO user) {
        if (user == null) return null;

        return UserEntity
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .build();
    }

    public static UserEntity toEntity(RegisterRequestDTO user) {
        if (user == null) return null;

        return UserEntity
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .build();
    }
    public static UserEntity toEntity(RegisterRequestDTOCsv user) {
        if (user == null) return null;

        return UserEntity
                .builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .cpfCnpj(user.getCpfCnpj())
                .telefone(user.getTelefone())
                .senha(user.getPassword())
                .role(user.getRole())
                .build();
    }
}