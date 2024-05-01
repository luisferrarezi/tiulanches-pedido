package br.com.fiap.tiulanches.utils.pedido;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.tiulanches.adapter.repository.pedido.ItemPedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.core.entity.pedido.ItemPedido;
import br.com.fiap.tiulanches.core.entity.pedido.Pedido;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.utils.cliente.ClientePadrao;

public class PedidoPadrao {

    private ItemPedidoPadrao itemPedidoPadrao;
    private List<ItemPedido> listItemPedido = new ArrayList<>();
    private List<ItemPedidoDto> listItemPedidoDto = new ArrayList<>();
    private ClientePadrao clientePadrao;

    public PedidoPadrao(){
        itemPedidoPadrao = new ItemPedidoPadrao();
        listItemPedido.add(itemPedidoPadrao.createItemPedido());
        listItemPedidoDto.add(itemPedidoPadrao.createItemPedidoDto());

        clientePadrao = new ClientePadrao();
    }

    public Pedido createPedido(){
        return new Pedido((Long) PedidoEnum.ID_PEDIDO.getValor(),
                          clientePadrao.createClient(),
                          StatusPedido.RECEBIDO,
                          listItemPedido);
    }

    public PedidoDto createPedidoDto(){
        return new PedidoDto((Long) PedidoEnum.ID_PEDIDO.getValor(),
                             clientePadrao.createClientDto(),
                             StatusPedido.RECEBIDO,
                             listItemPedidoDto);
    }    
}
