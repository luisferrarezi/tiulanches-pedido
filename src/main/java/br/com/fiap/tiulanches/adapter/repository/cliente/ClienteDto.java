package br.com.fiap.tiulanches.adapter.repository.cliente;

import br.com.fiap.tiulanches.core.entity.cliente.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;

public record ClienteDto(
		@Schema(description = "CPF do cliente sem formatação", example = "70636213005", maxLength = 11)
		String cpf, 
		@Schema(description = "Nome do cliente", example = "Luis Antonio", maxLength = 60)
		String nome, 
		@Schema(description = "Email do cliente", example = "luisantonio@gmail.com", maxLength = 60)
		String email) {
	
	public ClienteDto(Cliente cliente) {
		this(cliente.getCpf(), cliente.getNome(), cliente.getEmail());
	}
}
