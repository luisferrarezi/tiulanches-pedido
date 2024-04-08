package br.com.fiap.tiulanches.infra.kafka.produto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.mensagem.produto.ProdutoEvent;
import br.com.fiap.tiulanches.adapter.mensagem.produto.ProdutoListener;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoRepository;
import br.com.fiap.tiulanches.core.entitie.produto.Produto;

@Service
public class ProdutoService implements ProdutoListener {
    
	private final ProdutoRepository repository; 	

	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;		
	}
    
    @Override    
    @KafkaListener(topics = "topico-produto-pedido", groupId = "grupo-produto-pedido")
    public void processaMensagem(ProdutoEvent produtoEvent) {
        Produto produto = new Produto();
        produto.cadastrar(produtoEvent.getProdutoDto());

        if (produtoEvent.getEvento().equals("CREATE")) {
            repository.save(produto);
        }
    }
}
