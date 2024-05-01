package br.com.fiap.tiulanches.adapter.repository.painelpedido;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.tiulanches.core.entity.painelpedido.PainelPedido;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.utils.painelPedido.PainelPedidoPadrao;
import br.com.fiap.tiulanches.utils.pedido.PedidoEnum;

class PainelPedidoDtoTest {

    private PainelPedidoDto painelPedidoDto;
    private PainelPedidoPadrao painelPedidoPadrao;

    private final Long idPedido = (Long) PedidoEnum.ID_PEDIDO.getValor();

    @BeforeEach
    void beforeEach(){
        painelPedidoPadrao = new PainelPedidoPadrao();
    }

    @Test
    void constructorAllArgumentsTest(){
        painelPedidoDto = painelPedidoPadrao.creatPainelPedidoDto();

        assertEquals(idPedido, painelPedidoDto.idPedido());
        assertEquals(StatusPedido.RECEBIDO, painelPedidoDto.status());
    }

    @Test
    void constructorByPainelPedidoTest(){
        PainelPedido painelPedido = painelPedidoPadrao.creatPainelPedido();
        painelPedidoDto = new PainelPedidoDto(painelPedido);

        assertEquals(idPedido, painelPedidoDto.idPedido());
        assertEquals(StatusPedido.RECEBIDO, painelPedidoDto.status());
    }
}
