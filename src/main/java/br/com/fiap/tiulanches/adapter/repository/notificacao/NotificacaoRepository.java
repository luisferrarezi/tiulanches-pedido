package br.com.fiap.tiulanches.adapter.repository.notificacao;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.tiulanches.core.entity.notificacao.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
}
