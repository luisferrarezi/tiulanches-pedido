package br.com.fiap.tiulanches.adapter.controller;

import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoDto;

public interface ProdutoController {	
	public void cadastrar(ProdutoDto dto);	
	public void alterar(ProdutoDto dto);
	public void excluir(ProdutoDto dto);
}
