package br.com.fiap.tiulanchespedido.adapter.repository.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tiulanchespedido.core.entitie.pedido.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{
}
