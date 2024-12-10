package com.example.CRUD.service;

import com.example.CRUD.entity.EnderecoEntity;
import com.example.CRUD.repository.EnderecoRepository;
import com.microsoft.azure.functions.annotation.FunctionName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    public EnderecoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    @DisplayName("Salvar um novo endereço com sucesso")
    @Test
    void testSave_Success() {
        EnderecoEntity novoEndereco = new EnderecoEntity();
        novoEndereco.setCep("12345-678");
        novoEndereco.setNumero(10);

        when(enderecoRepository.findByCepAndNumero("12345-678", 10)).thenReturn(Optional.empty());
        when(enderecoRepository.save(novoEndereco)).thenReturn(novoEndereco);

        EnderecoEntity result = enderecoService.save(novoEndereco, 10);

        assertNotNull(result);
        assertEquals(novoEndereco, result);
        verify(enderecoRepository, times(1)).save(novoEndereco);
    }


    @DisplayName("Salvar um endereço já existente")
    @Test
    void testSave_Conflict() {
        EnderecoEntity novoEndereco = new EnderecoEntity();
        novoEndereco.setCep("12345-678");
        novoEndereco.setNumero(10);

        when(enderecoRepository.findByCepAndNumero("12345-678", 10)).thenReturn(Optional.of(novoEndereco));

        assertThrows(ResponseStatusException.class, () -> enderecoService.save(novoEndereco, 10));
        verify(enderecoRepository, never()).save(any());
    }


    @DisplayName("Listar endereços com sucesso")
    @Test
    void testListarEnderecos_Success() {
        List<EnderecoEntity> mockList = new ArrayList<>();
        mockList.add(new EnderecoEntity());
        mockList.add(new EnderecoEntity());

        when(enderecoRepository.findAll()).thenReturn(mockList);

        List<EnderecoEntity> result = enderecoService.listarEnderecos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(enderecoRepository, times(1)).findAll();
    }


    @DisplayName("Listar endereços vazios")
    @Test
    void testListarEnderecos_NoContent() {
        when(enderecoRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> enderecoService.listarEnderecos());
        assertEquals("Não a endereços cadastrados", exception.getReason());
        verify(enderecoRepository, times(1)).findAll();
    }


    @DisplayName("Buscar endereço por ID inexistente")
    @Test
    void testBuscarPorId_NotFound() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> enderecoService.buscarPorId(1));
        assertEquals("Endereço não encontrado!", exception.getReason());
        verify(enderecoRepository, times(1)).findById(1);
    }
}