package br.com.fiap.tiulanches.adapter.repository.notificacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.tiulanches.core.entity.notificacao.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
    @Query(value = "SELECT count(id_notificacao) FROM notificacoes " + 
                   " WHERE id_pedido = :#{#idPedido} ", nativeQuery = true)
	int geNotificacaoPedido(@Param("idPedido") long idPedido);
}
