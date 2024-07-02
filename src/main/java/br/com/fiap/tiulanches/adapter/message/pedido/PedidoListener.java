package br.com.fiap.tiulanches.adapter.message.pedido;

import br.com.fiap.tiulanches.adapter.message.pagamento.PagamentoEvent;

public interface PedidoListener {
    public void processaStatusMensagem(PedidoEvent pedidoEvent);
    public void processaPagamentoMensagem(PagamentoEvent pagamentoEvent);
}
