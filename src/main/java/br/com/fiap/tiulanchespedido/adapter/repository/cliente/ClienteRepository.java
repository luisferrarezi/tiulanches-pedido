package br.com.fiap.tiulanchespedido.adapter.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tiulanchespedido.core.entitie.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{
}
