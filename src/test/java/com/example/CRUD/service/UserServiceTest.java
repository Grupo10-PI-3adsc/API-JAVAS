package com.example.CRUD.service;


import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.exception.JaCadastradoException;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.security.securityToken.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um novo usuário com sucesso")
    void save_DeveSalvarNovoUsuario() {

        UserEntity userMock = new UserEntity();
        userMock.setEmail("teste@example.com");

        when(userRepository.findByEmail("teste@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(userMock)).thenReturn(userMock);


        UserEntity resultado = userService.save(userMock);


        assertNotNull(resultado);
        verify(userRepository, times(1)).save(userMock);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar um usuário já cadastrado")
    void save_DeveLancarExcecaoSeUsuarioJaExistir() {

        UserEntity userMock = new UserEntity();
        userMock.setEmail("teste@example.com");

        when(userRepository.findByEmail("teste@example.com")).thenReturn(Optional.of(userMock));


        assertThrows(JaCadastradoException.class, () -> userService.save(userMock));
        verify(userRepository, never()).save(userMock);
    }

    @Test
    @DisplayName("Deve retornar lista de todos os clientes")
    void listarCliente_DeveRetornarTodosClientes() {

        List<UserEntity> mockUsers = List.of(new UserEntity(), new UserEntity());
        when(userRepository.findAll()).thenReturn(mockUsers);


        List<UserEntity> resultado = userService.listarCliente();


        assertEquals(2, resultado.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve inativar cliente com sucesso")
    void inativarCliente_DeveInativarClienteComSucesso() {

        int userId = 1;
        UserEntity userMock = new UserEntity();
        userMock.setId(userId);
        userMock.setIsActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));


        UserEntity resultado = userService.inativarCliente(userId);


        assertFalse(resultado.getIsActive());
        verify(userRepository, times(1)).save(userMock);
    };

}
