package br.com.fiap.tiulanches.core.entity.cliente;

import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;
import br.com.fiap.tiulanches.core.enums.Logado;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
	@Schema(description = "CPF do cliente sem formatação", example = "70636213005", maxLength = 11, minLength = 11)
	private String cpf;
	
	@Size(max=60)
	@Schema(description = "Nome do cliente", example = "Luis Antonio", maxLength = 60)
	private String nome;
	
	@Size(max=60)
	@Schema(description = "Email do cliente", example = "luisantonio@gmail.com", maxLength = 60)
	private String email;

	@Enumerated(EnumType.ORDINAL)
	@Schema(implementation = Logado.class, description = "Define se o cliente está logado na aplicação", example = "SIM")	
	private Logado logado;

	@NotBlank
	@Size(max=100)
	@Schema(description = "Endereço do cliente", example = "Rua Augustina", maxLength = 100)
	private String endereco;

	@NotBlank
	@Size(max=10)
	@Schema(description = "Número endereço do Cliente", example = "1234", maxLength = 10)
	private String numero;

	@NotBlank
	@Size(max=100)
	@Schema(description = "Bairro do cliente", example = "Santa Luzia", maxLength = 100)
	private String bairro;

	@NotBlank
	@Size(max=100)
	@Schema(description = "Cidade do cliente", example = "Potirendaba", maxLength = 100)
	private String cidade;

	@NotBlank
	@Size(max=2)
	@Schema(description = "Estado cidade do cliente", example = "SP", maxLength = 2)
	private String estado;

	@NotBlank
	@Size(max=9)
	@Schema(description = "CEP do cliente", example = "11111-000", maxLength = 9)
	private String cep;

	@NotBlank
	@Size(max=15)
	@Schema(description = "Telefone do cliente", example = "(11) 99999-9999", maxLength = 15)
	private String telefone;

	public boolean isLogado(){
		return this.logado == Logado.SIM;
	}

	public void cadastrar(ClienteDto clienteDto) {
		this.cpf 	  = clienteDto.cpf();
		this.email 	  = clienteDto.email();
		this.nome 	  = clienteDto.nome();
		this.endereco = clienteDto.endereco();
		this.numero   = clienteDto.numero();
		this.bairro   = clienteDto.bairro();
		this.cidade   = clienteDto.cidade();
		this.estado   = clienteDto.estado();
		this.cep 	  = clienteDto.cep();
		this.telefone = clienteDto.telefone();
		this.logado   = Logado.NAO;		
	}		

	public void atualizar(ClienteDto clienteDto) {
		this.email 	  = clienteDto.email();
		this.nome 	  = clienteDto.nome();
		this.endereco = clienteDto.endereco();
		this.numero   = clienteDto.numero();
		this.bairro   = clienteDto.bairro();
		this.cidade   = clienteDto.cidade();
		this.estado   = clienteDto.estado();
		this.cep 	  = clienteDto.cep();
		this.telefone = clienteDto.telefone();
	}				
}
