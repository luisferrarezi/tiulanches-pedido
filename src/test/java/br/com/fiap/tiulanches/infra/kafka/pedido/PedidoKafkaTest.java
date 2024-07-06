package br.com.fiap.tiulanches.infra.kafka.pedido;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoEvent;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;

class PedidoKafkaTest {

    @Mock
    private PedidoController controller;    
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
    void testProcessaStatusMensagem() {
        PedidoKafka pedidoKafka;
        pedidoKafka = new PedidoKafka(controller);

        PedidoEvent pedidoEvent = new PedidoEvent();
        pedidoEvent.setPedidoDto(new PedidoPadrao().createPedidoDto());

        pedidoEvent.setEvento(EventoEnum.CREATE);
        doNothing().when(controller).atualizaStatus(any(PedidoDto.class));
        assertDoesNotThrow(()->pedidoKafka.processaStatusMensagem(pedidoEvent));

        pedidoEvent.setEvento(EventoEnum.UPDATE);        
        assertDoesNotThrow(()->pedidoKafka.processaStatusMensagem(pedidoEvent));
    }
}
