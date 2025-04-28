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

        return LoginResponseDTO
                .builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nome(user.getNome())
                .token(token)
                .endereco(
                        LoginResponseDTO.UsuarioEnderecoResponseDto
                                .builder()
                                .bairro(endereco.getBairro())
                                .cep(endereco.getCep())
                                .uf(endereco.getUf())
                                .localidade(endereco.getLocalidade())
                                .build()
                )
                .build();

    }


    public static UserDTOResponse toDTOEnd(UserEntity user) {
        if (user == null) return null;

        EnderecoEntity endereco = user.getFkEndereco();

        if (endereco != null)

            return UserDTOResponse
                    .builder()
                    .nome(user.getNome())
                    .email(user.getEmail())
                    .cpfCnpj(user.getCpfCnpj())
                    .telefone(user.getTelefone())
                    .senha(user.getPassword())
                    .role(user.getRole())
                    .endereco(
                            UserDTOResponse.UsuarioEnderecoResponseDto
                                    .builder()
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
