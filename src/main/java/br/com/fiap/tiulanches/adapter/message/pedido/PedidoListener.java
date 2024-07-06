package br.com.fiap.tiulanches.adapter.message.pedido;

public interface PedidoListener {
    public void processaStatusMensagem(PedidoEvent pedidoEvent);    
}
