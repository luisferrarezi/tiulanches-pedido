package br.com.fiap.tiulanches.infra.kafka.cliente;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.controller.ClienteController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.cliente.ClienteEvent;
import br.com.fiap.tiulanches.adapter.message.cliente.ClienteListener;

@Service
public class ClienteKafka implements ClienteListener {
    
	private final ClienteController controller; 	

	public ClienteKafka(ClienteController controller) {
		this.controller = controller;		
	}
    
    @Override    
    @KafkaListener(topics = "topico-cliente-pedido", groupId = "grupo-cliente-pedido")
    public void processaMensagem(ClienteEvent clienteEvent) {
        switch (clienteEvent.getEvento()){
            case EventoEnum.CREATE: 
                controller.cadastrar(clienteEvent.getClienteDto());
                break;
            case EventoEnum.UPDATE: 
                controller.alterar(clienteEvent.getClienteDto());
                break;              
            case EventoEnum.DELETE: 
                controller.excluir(clienteEvent.getClienteDto());
                break;                                
            default: 
                break;
        }
    }
}
