package br.com.fiap.tiulanches.infra.kafka.cliente;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.controller.ClienteController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.cliente.ClienteEvent;
import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;
import br.com.fiap.tiulanches.utils.cliente.ClientePadrao;

class ClienteKafkaTest {

    @Mock
    private ClienteController controller;
    private AutoCloseable openMocks;

    @BeforeEach
    void beforeEach(){        
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        openMocks.close();
    }

    @Test
    void testProcessaMensagem(){
        ClienteKafka clienteKafka;
        clienteKafka = new ClienteKafka(controller);

        ClienteEvent clienteEvent = new ClienteEvent();
        clienteEvent.setClienteDto(new ClientePadrao().createClientDto());
        
        clienteEvent.setEvento(EventoEnum.CREATE);
        doNothing().when(controller).cadastrar(any(ClienteDto.class));
        assertDoesNotThrow(()->clienteKafka.processaMensagem(clienteEvent));

        clienteEvent.setEvento(EventoEnum.UPDATE);
        doNothing().when(controller).alterar(any(ClienteDto.class));
        assertDoesNotThrow(()->clienteKafka.processaMensagem(clienteEvent));

        clienteEvent.setEvento(EventoEnum.DELETE);
        doNothing().when(controller).excluir(any(ClienteDto.class));
        assertDoesNotThrow(()->clienteKafka.processaMensagem(clienteEvent));        
    }
}
