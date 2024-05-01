package br.com.fiap.tiulanches.core.entity.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.utils.pedido.ItemPedidoPadrao;
import br.com.fiap.tiulanches.utils.pedido.PedidoEnum;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;

class PedidoTest {
    
    private Pedido pedido;
    private PedidoPadrao pedidoPadrao;
    private ItemPedidoPadrao itemPedidoPadrao;

    private final Long idPedido = (Long) PedidoEnum.ID_PEDIDO.getValor();

    @BeforeEach
    void beforeEach(){
        pedidoPadrao = new PedidoPadrao();
        pedido = pedidoPadrao.createPedido();
        itemPedidoPadrao = new ItemPedidoPadrao();
    }

    @Test
    void constructorAllArgumentsTest(){
        assertEquals(idPedido, pedido.getIdPedido());
        assertEquals(StatusPedido.RECEBIDO, pedido.getStatus());
        assertEquals(1, pedido.getListItemPedido().size());
    }

    @Test
    void adicionarItemTest(){
        ItemPedido itemPedido = itemPedidoPadrao.createItemPedido();

        pedido.adicionarItem(itemPedido);
        assertEquals(2, pedido.getListItemPedido().size());
    }

    @Test
    void removerItemTest(){
        ItemPedido itemPedido = itemPedidoPadrao.createItemPedido();
        
        pedido.removerItem(itemPedido);
        assertEquals(1, pedido.getListItemPedido().size());
    }

    @Test
    void cadastrarTest(){
        PedidoDto pedidoDto = pedidoPadrao.createPedidoDto();

        pedido.cadastrar(pedidoDto);

        assertEquals(idPedido, pedido.getIdPedido());
        assertEquals(StatusPedido.RECEBIDO, pedido.getStatus());
    }

}
