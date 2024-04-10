package br.com.fiap.tiulanches.adapter.controller;

import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;

public interface ClienteController {	
	public void cadastrar(ClienteDto dto);	
	public void alterar(ClienteDto dto);
	public void excluir(ClienteDto dto);
}
