package br.com.fiap.tiulanchespedido.adapter.repository.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.tiulanchespedido.core.entitie.pagamento.Pagamento;

public interface PagamentoRepository extends JpaRepository <Pagamento, Long>{	
}
