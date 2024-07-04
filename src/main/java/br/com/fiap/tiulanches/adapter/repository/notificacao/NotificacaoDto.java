package br.com.fiap.tiulanches.adapter.repository.notificacao;

import br.com.fiap.tiulanches.core.entity.notificacao.Notificacao;
import io.swagger.v3.oas.annotations.media.Schema;

public record NotificacaoDto(
		@Schema(description = "Código da notificação após ser criada", example = "1")
		long idNotificacao,		
		@Schema(description = "Código do pedido", example = "1")
		long idPedido,
		@Schema(description = "Notificação enviada para o cliente", example = "Olá fulano de tal...", maxLength = 100)
		String mensagem) {
	
	public NotificacaoDto(Notificacao notificacao) {
		this(notificacao.getIdNotificacao(),
			 notificacao.getIdPedido(),
			 notificacao.getMensagem());
	}
}
