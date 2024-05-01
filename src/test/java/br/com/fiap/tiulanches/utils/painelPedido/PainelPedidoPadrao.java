package br.com.fiap.tiulanches.utils.painelPedido;

import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanches.core.entity.painelpedido.PainelPedido;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.utils.pedido.PedidoEnum;

public class PainelPedidoPadrao {

    public PainelPedido creatPainelPedido(){
        return new PainelPedido((Long) PedidoEnum.ID_PEDIDO.getValor(),
                                StatusPedido.RECEBIDO);
    }

    public PainelPedidoDto creatPainelPedidoDto(){
        return new PainelPedidoDto((Long) PedidoEnum.ID_PEDIDO.getValor(),
                                   StatusPedido.RECEBIDO);
    }    
}
