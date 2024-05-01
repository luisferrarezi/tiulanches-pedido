package br.com.fiap.tiulanches.core.entity.produto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoDto;
import br.com.fiap.tiulanches.core.enums.Categoria;
import br.com.fiap.tiulanches.utils.produto.ProdutoEnum;
import br.com.fiap.tiulanches.utils.produto.ProdutoPadrao;

class ProdutoTest {
    
    private Produto produto;
    private ProdutoDto produtoDto;
    private ProdutoPadrao produtoPadrao;

    private final Long idProdutoPadrao = (Long) ProdutoEnum.ID_PRODUTO.getValor();
    private final String nomePadrao = (String) ProdutoEnum.NOME.getValor();
    private final String descricaoPadrao = (String) ProdutoEnum.DESCRICAO.getValor();
    private final BigDecimal precoPadrao = (BigDecimal) ProdutoEnum.PRECO.getValor();
    private final Integer tempoPreparoPadrao = (Integer) ProdutoEnum.TEMPO_PREPARO.getValor();
    private final String imagemPadrao = (String) ProdutoEnum.IMAGEM.getValor();

    @BeforeEach
    void beforeEach(){
        produtoPadrao = new ProdutoPadrao();
    }

    @Test
    void constructorAllArgumentsTest(){
        produto = produtoPadrao.createProduto();

        assertEquals(idProdutoPadrao.longValue(), produto.getIdProduto());
        assertEquals(Categoria.ACOMPANHAMENTO, produto.getCategoria());
        assertEquals(nomePadrao, produto.getNome());
        assertEquals(descricaoPadrao, produto.getDescricao());
        assertEquals(precoPadrao, produto.getPreco());
        assertEquals(tempoPreparoPadrao.intValue(), produto.getTempoPreparo());
        assertEquals(imagemPadrao, produto.getImagem());
    }

    @Test
    void constructorByProdutoTest(){
        produto = new Produto();

        produto.setIdProduto(idProdutoPadrao);
        produto.setCategoria(Categoria.ACOMPANHAMENTO);
        produto.setNome(nomePadrao);
        produto.setDescricao(descricaoPadrao);
        produto.setPreco(precoPadrao);
        produto.setTempoPreparo(tempoPreparoPadrao);
        produto.setImagem(imagemPadrao);

        assertEquals(idProdutoPadrao.longValue(), produto.getIdProduto());
        assertEquals(Categoria.ACOMPANHAMENTO, produto.getCategoria());
        assertEquals(nomePadrao, produto.getNome());
        assertEquals(descricaoPadrao, produto.getDescricao());
        assertEquals(precoPadrao, produto.getPreco());
        assertEquals(tempoPreparoPadrao.intValue(), produto.getTempoPreparo());
        assertEquals(imagemPadrao, produto.getImagem());
    }
    
    @Test
    void equalsTest(){
        produto = produtoPadrao.createProduto();
        Produto produto2 = produtoPadrao.createProduto();

        assertDoesNotThrow(()->produto.equals(produto2));
    }

    @Test
    void hashCodeTest(){
        produto = produtoPadrao.createProduto();

        assertDoesNotThrow(()->produto.hashCode());
    }

    @Test
    void produtoCadastrarTest(){
        produtoDto = produtoPadrao.createProdutoDto();

        produto = new Produto();
        produto.cadastrar(produtoDto);
        
        assertEquals(Categoria.ACOMPANHAMENTO, produto.getCategoria());
        assertEquals(nomePadrao, produto.getNome());
        assertEquals(descricaoPadrao, produto.getDescricao());
        assertEquals(precoPadrao, produto.getPreco());
        assertEquals(tempoPreparoPadrao.intValue(), produto.getTempoPreparo());
        assertEquals(imagemPadrao, produto.getImagem());
    }    
   
    @Test
    void produtoCadastrarNullTest(){
        produtoDto = new ProdutoDto(idProdutoPadrao, null, null, null, null, 0, null);

        produto = new Produto();
        produto.cadastrar(produtoDto);
        
        assertEquals(null, produto.getCategoria());
        assertEquals(null, produto.getNome());
        assertEquals(null, produto.getDescricao());
        assertEquals(null, produto.getPreco());
        assertEquals(0, produto.getTempoPreparo());
        assertEquals(null, produto.getImagem());
    }            
}
