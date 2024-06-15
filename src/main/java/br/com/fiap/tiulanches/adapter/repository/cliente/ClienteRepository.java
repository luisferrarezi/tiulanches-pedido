package br.com.fiap.tiulanches.adapter.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.tiulanches.core.entity.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{

	@Modifying
   	@Query(value = "UPDATE pedidos set cpf = null " + 
                   " WHERE cpf = :#{#cpf} ", nativeQuery = true)
	void apagaClientePedidosLGPD(@Param("cpf") String cpf);
}
