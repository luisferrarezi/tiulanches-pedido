package br.com.fiap.tiulanches.infra.kafka.pedido;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoEvent;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;

class EnviaPedidoTest {

    private EnviaPedido enviaPedido;
    private PedidoPadrao pedidoPadrao;

    @Mock
    KafkaTemplate<String, Object> kafka;

    @BeforeEach
    @SuppressWarnings("unchecked")    
    void beforeEach(){
        kafka = Mockito.mock(KafkaTemplate.class);
        enviaPedido = new EnviaPedido(kafka);
        pedidoPadrao = new PedidoPadrao();
    }

    @Test
    void constructorEnviaPedidoTest(){
        enviaPedido = new EnviaPedido(kafka);

        assertNotEquals(null, enviaPedido);
    }

    @Test
    void enviaMensagemTest(){
        PedidoDto pedidoDto;
        pedidoDto = pedidoPadrao.createPedidoDto();

        when(kafka.send(anyString(), any(PedidoEvent.class))).thenReturn(null);
        assertDoesNotThrow(()->enviaPedido.enviaMensagem(EventoEnum.CREATE, pedidoDto));
    }    

    @Test
    void enviaPedidoMensagemTest(){
        PedidoDto pedidoDto;
        pedidoDto = pedidoPadrao.createPedidoDto();

        when(kafka.send(anyString(), any(PedidoEvent.class))).thenReturn(null);
        assertDoesNotThrow(()->enviaPedido.enviaPedidoMensagem(EventoEnum.CREATE, pedidoDto));
    }        
}
