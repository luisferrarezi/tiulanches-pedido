package br.com.fiap.tiulanchespedido.adapter.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.tiulanchespedido.core.entitie.produto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
}
