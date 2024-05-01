package br.com.fiap.tiulanches.adapter.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tiulanches.core.entity.produto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
}
