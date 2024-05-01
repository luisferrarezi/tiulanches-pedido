package br.com.fiap.tiulanches.core.entity.cliente;

import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;
import br.com.fiap.tiulanches.core.enums.Logado;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Cliente")
@Table(name = "CLIENTES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class Cliente {
	@Id	
	@Size(max=11, min=11)
	@Schema(description = "CPF do cliente sem formatação", example = "70636213005", required = true, maxLength = 11, minLength = 11)
	private String cpf;
	
	@Size(max=60)
	@Schema(description = "Nome do cliente", example = "Luis Antonio", required = true, maxLength = 60)
	private String nome;
	
	@Size(max=60)
	@Schema(description = "Email do cliente", example = "luisantonio@gmail.com", required = true, maxLength = 60)
	private String email;

	@Enumerated(EnumType.ORDINAL)
	@Schema(implementation = Logado.class, description = "Define se o cliente está logado na aplicação", example = "SIM", required = true)	
	private Logado logado;

	public boolean isLogado(){
		return this.logado == Logado.SIM;
	}

	public void cadastrar(ClienteDto clienteDto) {
		this.cpf 	= clienteDto.cpf();
		this.email 	= clienteDto.email();
		this.nome 	= clienteDto.nome();
		this.logado = Logado.NAO;		
	}		

	public void atualizar(ClienteDto clienteDto) {
		this.email 	= clienteDto.email();
		this.nome 	= clienteDto.nome();
	}				
}
