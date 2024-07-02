package br.com.fiap.tiulanches.infra.kafka.pedido;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.message.pagamento.PagamentoEvent;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoEvent;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoListener;

@Component
public class PedidoKafka implements PedidoListener {
    
	private final PedidoController pedidoController; 	

	public PedidoKafka(PedidoController pedidoController) {
		this.pedidoController = pedidoController;		
	}
    
    @Override    
    @KafkaListener(topics = "topico-producao-status-pedido", groupId = "grupo-pedido")
    public void processaStatusMensagem(PedidoEvent pedidoEvent) {
        pedidoController.atualizaStatus(pedidoEvent.getPedidoDto());
    }
   
    @Override
    @KafkaListener(topics = "topico-pagamento-pedido", groupId = "grupo-pedido")
    public void processaPagamentoMensagem(PagamentoEvent pagamentoEvent) {
        pedidoController.cancelaPedidoNaoPago(pagamentoEvent.getIdPedido());
    }
}
