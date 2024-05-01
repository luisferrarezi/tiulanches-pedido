package br.com.fiap.tiulanches.core.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;
import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteRepository;
import br.com.fiap.tiulanches.core.entity.cliente.Cliente;
import br.com.fiap.tiulanches.core.exception.BusinessException;
import br.com.fiap.tiulanches.utils.cliente.ClientePadrao;

class ClienteServiceTest {    
    private ClienteService clienteService;    
    private Optional<Cliente> clienteTest;
    private ClienteDto clienteDtoTest;    
    private ClientePadrao clientePadrao;
    
    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void beforeEach(){

        MockitoAnnotations.openMocks(this);

        clientePadrao = new ClientePadrao();
        clienteDtoTest = clientePadrao.createClientDto();
        clienteTest = Optional.ofNullable(clientePadrao.createClient());
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void testAlterar() {
        when(clienteRepository.findById(anyString())).thenReturn(clienteTest);        
        
        assertDoesNotThrow(()-> clienteService.alterar(clientePadrao.createClientDto()));
    }

    
    @Test
    void testCadastrar() {
        ClienteDto clienteDto = clientePadrao.createClientDto();

        BusinessException exception = assertThrows(BusinessException.class, ()-> clienteService.cadastrar(clienteDto));
        assertEquals("CPF não informado!", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());

        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(new Cliente()));
        exception = assertThrows(BusinessException.class, ()-> clienteService.cadastrar(clienteDtoTest));
        assertEquals("Cliente já cadastrado", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());

        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());
        assertDoesNotThrow(()-> clienteService.cadastrar(clienteDtoTest));
    }

    @Test
    void testExcluir() {
        when(clienteRepository.findById(anyString())).thenReturn(clienteTest);
        doNothing().when(clienteRepository).deleteById(anyString());
        assertDoesNotThrow(()-> clienteService.excluir(clientePadrao.createClientDto()));
        
        when(clienteRepository.findById(anyString())).thenReturn(clienteTest);
        BusinessException exception = assertThrows(BusinessException.class, ()-> clienteService.excluir(clientePadrao.createClientDto()));
        assertEquals("Cliente já utilizado em pedido, não pode ser excluído!", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}
