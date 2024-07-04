package br.com.fiap.tiulanches.core.entity.notificacao;

import br.com.fiap.tiulanches.adapter.repository.notificacao.NotificacaoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Notificacao")
@Table(name = "NOTIFICACOES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notificacao {
	@Id	
	@Schema(description = "Código da notificação após ser criada", example = "1")
	private long idNotificacao;
	
	@NotNull
	@Schema(description = "Código do pedido", example = "1")
	private long idPedido;
	
	@NotNull
	@NotBlank
	@Size(max=100)
	@Schema(description = "Notificação enviada para o cliente", example = "Olá fulano de tal...", maxLength = 100)
	private String mensagem;

	public void notificar(NotificacaoDto notificacaoDto) {
		this.idPedido = notificacaoDto.idPedido();
		this.mensagem = notificacaoDto.mensagem();
	}				
}
