package br.com.fiap.tiulanches.core.entitie.pedido;

import java.util.List;

import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.core.entitie.cliente.Cliente;
import br.com.fiap.tiulanches.core.enums.Logado;
import br.com.fiap.tiulanches.core.enums.StatusPedido;

import java.util.ArrayList;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Pedido")
@Table(name = "PEDIDOS")
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	@Id
	@Getter	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	@Schema(description = "Código do pedido após ser criado", example = "17", required = true)	
	private long idPedido;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="cpf")
	@Schema(description = "Cliente caso ele queira se identificar")	
	private Cliente cliente;
	
	@Getter	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Schema(implementation = StatusPedido.class, description = "Status do pedido", example = "RECEBIDO", required = true)	
	private StatusPedido status;		
    
	@Getter
	@Setter
	@OneToMany(cascade= CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id_pedido")
	@Schema(description = "Lista itens do pedido", required = true)	
	private List<ItemPedido> listItemPedido = new ArrayList<>();
	
	public void adicionarItem(ItemPedido item) {
		item.setIdPedido(this.idPedido);
		this.listItemPedido.add(item);			
	}
	
	public void removerItem(ItemPedido item) {
		this.listItemPedido.remove(item);			
	}	
	
	public void cadastrar(PedidoDto pedido) {
		this.status = StatusPedido.RECEBIDO;
		validaCliente(pedido.cliente());
	}	
	
	private void validaCliente(ClienteDto dto) {		
		if (dto != null) {
			this.cliente = new Cliente(dto.cpf(), dto.nome(), dto.email(), Logado.NAO);
		}
	}

	public void alteraStatus(StatusPedido status){
		this.status = status;
	}	
}
