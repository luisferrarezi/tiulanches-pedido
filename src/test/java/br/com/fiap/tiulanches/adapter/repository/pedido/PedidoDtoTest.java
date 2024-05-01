package br.com.fiap.tiulanches.adapter.repository.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.tiulanches.core.entity.pedido.Pedido;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.utils.pedido.PedidoEnum;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;

class PedidoDtoTest {

    private PedidoDto pedidoDto;
    private PedidoPadrao pedidoPadrao;

    private final Long idPedido = (Long) PedidoEnum.ID_PEDIDO.getValor();

    @BeforeEach
    void beforeEach(){
        pedidoPadrao = new PedidoPadrao();
    }

    @Test
    void constructorAllArgumentsTest(){
        pedidoDto = pedidoPadrao.createPedidoDto();
        
        assertEquals(idPedido, pedidoDto.idPedido());
        assertEquals(StatusPedido.RECEBIDO, pedidoDto.status());
        assertEquals(1, pedidoDto.listItemPedido().size());
    }

    @Test
    void constructorByProdutoTest(){
        Pedido pedido = pedidoPadrao.createPedido();
        pedidoDto = new PedidoDto(pedido);

        assertEquals(idPedido, pedidoDto.idPedido());
        assertEquals(StatusPedido.RECEBIDO, pedidoDto.status());
        assertEquals(1, pedidoDto.listItemPedido().size());
    }        
}
