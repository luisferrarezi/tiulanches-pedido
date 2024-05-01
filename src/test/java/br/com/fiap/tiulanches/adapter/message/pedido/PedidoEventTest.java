package br.com.fiap.tiulanches.adapter.message.pedido;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.tiulanches.adapter.message.EventoEnum;

class PedidoEventTest {

    private PedidoEvent pedidoEvent;
    
    @BeforeEach
    void beforeEach(){
        this.pedidoEvent = new PedidoEvent();
    }

    @Test
    void createTest(){
        pedidoEvent.setPedidoDto(null);        
        pedidoEvent.setEvento(EventoEnum.CREATE);

        assertEquals(EventoEnum.CREATE, pedidoEvent.getEvento());
        assertEquals(null, pedidoEvent.getPedidoDto());
    }

    @Test
    void constructorAllArgumentsTest(){
        pedidoEvent = new PedidoEvent(EventoEnum.CREATE, null);

        assertEquals(EventoEnum.CREATE, pedidoEvent.getEvento());
    }    

    @Test
    void noArgumentsTest(){
        pedidoEvent = new PedidoEvent();

        assertEquals(null, pedidoEvent.getEvento());
    }    

    @Test
    void equalsTest(){
        pedidoEvent = new PedidoEvent(EventoEnum.CREATE, null);
        PedidoEvent pedidoEvent2 = new PedidoEvent(EventoEnum.CREATE, null);

        assertDoesNotThrow(()->pedidoEvent.equals(pedidoEvent2));
    }

    @Test
    void hashCodeTest(){
        pedidoEvent = new PedidoEvent(EventoEnum.CREATE, null);        

        assertDoesNotThrow(()->pedidoEvent.hashCode());
    }

    @Test
    void toStringTest(){
        pedidoEvent = new PedidoEvent(EventoEnum.CREATE, null);        

        assertDoesNotThrow(()->pedidoEvent.toString());
    }
}
