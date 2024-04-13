package br.com.fiap.tiulanches.infra.kafka.pedido;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoEvent;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoListener;

@Service
public class PedidoKafka implements PedidoListener {
    
	private final PedidoController controller; 	

	public PedidoKafka(PedidoController controller) {
		this.controller = controller;		
	}
    
    @Override    
    @KafkaListener(topics = { "topico-producao-status-pedido", "topico-pagamento-pedido" }, groupId = "grupo-pedido")
    public void processaStatusMensagem(PedidoEvent pedidoEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        if (topic.equalsIgnoreCase("topico-producao-status-pedido")) {
            controller.atualizaStatus(pedidoEvent.getPedidoDto());
        } else 
        if (topic.equalsIgnoreCase("topico-pagamento-pedido") && 
            (pedidoEvent.getEvento() == EventoEnum.UPDATE)){
            controller.preparar(pedidoEvent.getPedidoDto().idPedido());
        }
    }
}
