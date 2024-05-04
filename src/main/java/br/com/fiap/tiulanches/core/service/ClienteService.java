package br.com.fiap.tiulanches.core.service;

import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;
import br.com.fiap.tiulanches.adapter.controller.ClienteController;
import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteRepository;
import br.com.fiap.tiulanches.core.entity.cliente.Cliente;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClienteService implements ClienteController {
	private final ClienteRepository repository; 

	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public void cadastrar(ClienteDto dto){
		Cliente cliente = new Cliente();
		cliente.cadastrar(dto);
		repository.save(cliente);
	}
	
	@Transactional	
	public void alterar(ClienteDto dto){
		Cliente cliente = repository.findById(dto.cpf()).orElseThrow(EntityNotFoundException::new);
		cliente.atualizar(dto);
	}	
	
	public void excluir(ClienteDto dto){
		Cliente cliente = repository.findById(dto.cpf()).orElseThrow(EntityNotFoundException::new);
		
		repository.deleteById(cliente.getCpf());
	}	
}