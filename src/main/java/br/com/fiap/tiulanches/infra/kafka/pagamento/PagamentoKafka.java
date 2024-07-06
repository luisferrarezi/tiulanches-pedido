package br.com.fiap.tiulanches.infra.kafka.pagamento;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.controller.NotificacaoController;
import br.com.fiap.tiulanches.adapter.message.pagamento.PagamentoEvent;
import br.com.fiap.tiulanches.adapter.message.pagamento.PagamentoListener;
import br.com.fiap.tiulanches.core.enums.Pago;

@Component
public class PagamentoKafka implements PagamentoListener {

	private final PedidoController pedidoController;
    private final NotificacaoController notificacaoController;

	public PagamentoKafka(PedidoController pedidoController, 
                          NotificacaoController notificacaoController) {
		this.pedidoController = pedidoController;
        this.notificacaoController = notificacaoController;
	}
   
    @Override
    @KafkaListener(topics = "topico-pagamento-pedido", groupId = "grupo-pedido")
    public void processaPagamentoMensagem(PagamentoEvent pagamentoEvent) {
        if (pagamentoEvent.getPago() == Pago.NAO){
            pedidoController.cancelaPedidoNaoPago(pagamentoEvent.getIdPedido());
            notificacaoController.notificar(pagamentoEvent.getIdPedido(), "Pedido cancelado por problema com o pagamento. Por favor, fale com sua operadora de pagamento.");
        } else {
            pedidoController.preparar(pagamentoEvent.getIdPedido());
            notificacaoController.notificar(pagamentoEvent.getIdPedido(), "Pagamento confirmado, seu pedido começou a ser preparado");
        }    
    }
}
