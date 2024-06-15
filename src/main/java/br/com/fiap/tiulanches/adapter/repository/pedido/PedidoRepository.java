package br.com.fiap.tiulanches.adapter.repository.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.tiulanches.core.entity.pedido.Pedido;
import br.com.fiap.tiulanches.core.enums.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	@Query(value = "SELECT pe.* " + 
                   "  FROM tlpedidos.pedidos pe " +
                   " WHERE pe.status = :#{#status?.ordinal()} ", nativeQuery = true)
	List<Pedido> findByStatus(@Param("status") StatusPedido status);
}
