package br.com.fiap.tiulanches.core.service;

import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoDto;
import br.com.fiap.tiulanches.adapter.controller.ProdutoController;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoRepository;
import br.com.fiap.tiulanches.core.entity.produto.Produto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService implements ProdutoController {
	private final ProdutoRepository repository; 

	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	@Transactional		
	public void cadastrar(ProdutoDto dto){
		Produto produto = new Produto();
		produto.cadastrar(dto);
		repository.save(produto);
	}
	
	@Transactional	
	public void alterar(ProdutoDto dto){
		Produto produto = repository.findById(dto.idProduto()).orElseThrow(EntityNotFoundException::new);
		produto.atualizar(dto);
	}	
	
	@Transactional		
	public void excluir(ProdutoDto dto){
		Produto produto = repository.findById(dto.idProduto()).orElseThrow(EntityNotFoundException::new);
		
		repository.deleteById(produto.getIdProduto());
	}	
}