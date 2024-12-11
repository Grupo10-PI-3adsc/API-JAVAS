package com.example.CRUD.service;

import com.example.CRUD.entity.MaoDeObrEntity;
import com.example.CRUD.entity.VeiculoEntity;
import com.example.CRUD.repository.MaoDeObraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaoDeObraServiceTest {

    @Mock
    private MaoDeObraRepository maoDeObraRepository;

    @Mock
    private VeiculoService veiculoService;

    @InjectMocks
    private MaoDeObraService maoDeObraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os serviços")
    void listar_DeveRetornarTodosOsServicos() {

        List<MaoDeObrEntity> mockServicos = List.of(new MaoDeObrEntity(), new MaoDeObrEntity());
        when(maoDeObraRepository.findAll()).thenReturn(mockServicos);


        List<MaoDeObrEntity> resultado = maoDeObraService.listar();


        assertEquals(2, resultado.size());
        verify(maoDeObraRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar serviços para um cliente")
    void pesquisarPorCliente_DeveRetornarServicosPorCliente() {

        int idCliente = 1;
        List<MaoDeObrEntity> mockServicos = List.of(new MaoDeObrEntity(), new MaoDeObrEntity());
        when(maoDeObraRepository.findAllByfkUser(idCliente)).thenReturn(mockServicos);


        List<MaoDeObrEntity> resultado = maoDeObraService.pesquisarPorCliente(idCliente);


        assertEquals(2, resultado.size());
        verify(maoDeObraRepository, times(1)).findAllByfkUser(idCliente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando serviço não encontrado por ID")
    void servicoPorId_DeveLancarExcecaoQuandoNaoEncontrado() {

        int idServico = 1;
        when(maoDeObraRepository.findById(idServico)).thenReturn(Optional.empty());


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            maoDeObraService.servicoPorId(idServico);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(maoDeObraRepository, times(1)).findById(idServico);
    }

    @Test
    @DisplayName("Deve salvar um novo serviço com sucesso")
    void adicionarServico_DeveSalvarNovoServico() {

        int idVeiculo = 1;
        VeiculoEntity veiculoMock = new VeiculoEntity();
        MaoDeObrEntity novoServico = new MaoDeObrEntity();
        MaoDeObrEntity servicoSalvo = new MaoDeObrEntity();
        servicoSalvo.setId(1);
        when(veiculoService.getById(idVeiculo)).thenReturn(veiculoMock);
        when(maoDeObraRepository.save(any(MaoDeObrEntity.class))).thenReturn(servicoSalvo);


        MaoDeObrEntity resultado = maoDeObraService.adicionarServico(novoServico, idVeiculo);


        assertNotNull(resultado.getId());
        verify(veiculoService, times(1)).getById(idVeiculo);
        verify(maoDeObraRepository, times(1)).save(any(MaoDeObrEntity.class));
    }

    @Test
    @DisplayName("Deve atualizar o status do serviço para 'Cancelado'")
    void cancelarServico_DeveAtualizarStatusParaCancelado() {

        int idCliente = 1;
        MaoDeObrEntity servicoMock = new MaoDeObrEntity();
        servicoMock.setStatus("Ativo");
        when(maoDeObraRepository.findByfkUser(idCliente)).thenReturn(Optional.of(servicoMock));
        when(maoDeObraRepository.save(any(MaoDeObrEntity.class))).thenReturn(servicoMock);


        MaoDeObrEntity resultado = maoDeObraService.cancelarServico(idCliente);


        assertEquals("Cancelado", resultado.getStatus());
        verify(maoDeObraRepository, times(1)).findByfkUser(idCliente);
        verify(maoDeObraRepository, times(1)).save(servicoMock);
    }
}
