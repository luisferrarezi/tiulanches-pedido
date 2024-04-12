package br.com.fiap.tiulanches.infra.kafka.produto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.controller.ProdutoController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.produto.ProdutoEvent;
import br.com.fiap.tiulanches.adapter.message.produto.ProdutoListener;

@Service
public class ProdutoKafka implements ProdutoListener {
    
	private final ProdutoController controller; 	

	public ProdutoKafka(ProdutoController controller) {
		this.controller = controller;		
	}
    
    @Override    
    @KafkaListener(topics = "topico-produto-pedido", groupId = "grupo-pedido")
    public void processaMensagem(ProdutoEvent produtoEvent) {
        switch (produtoEvent.getEvento()){
            case EventoEnum.CREATE: 
                controller.cadastrar(produtoEvent.getProdutoDto());
                break;
            case EventoEnum.UPDATE: 
                controller.alterar(produtoEvent.getProdutoDto());
                break;              
            case EventoEnum.DELETE: 
                controller.excluir(produtoEvent.getProdutoDto());
                break;                                
            default: 
                break;
        }
    }
}
