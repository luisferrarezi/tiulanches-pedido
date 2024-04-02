package br.com.fiap.tiulanchespedido.adapter.repository.painelpedido;

import br.com.fiap.tiulanchespedido.core.entitie.painelpedido.PainelPedido;
import br.com.fiap.tiulanchespedido.core.enums.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

public record PainelPedidoDto(@Schema(description = "Código do pedido após ser criado", example = "17", required = true)
							  long idPedido,
							  @Schema(implementation = StatusPedido.class, description = "Status do pedido", example = "RECEBIDO", required = true)						 
							  StatusPedido status) {	
	public PainelPedidoDto(PainelPedido painelPedido) {
		 this(painelPedido.getIdPedido(), painelPedido.getStatus());
	}	
}
