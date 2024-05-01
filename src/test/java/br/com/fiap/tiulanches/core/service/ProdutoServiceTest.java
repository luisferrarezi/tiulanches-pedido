package br.com.fiap.tiulanches.core.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoDto;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoRepository;
import br.com.fiap.tiulanches.core.entity.produto.Produto;
import br.com.fiap.tiulanches.utils.produto.ProdutoPadrao;

class ProdutoServiceTest {
    private ProdutoService produtoService;
    private Optional<Produto> produtoTeste;
    private ProdutoDto produtoDtoTeste;
    private ProdutoPadrao produtoPadrao;

    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void beforeEach(){
        openMocks = MockitoAnnotations.openMocks(this);
        
        produtoPadrao = new ProdutoPadrao();
        produtoDtoTeste = produtoPadrao.createProdutoDto();
        produtoTeste = Optional.ofNullable(produtoPadrao.createProduto());
        produtoService = new ProdutoService(produtoRepository);
    }

    @AfterEach
    void afterEach() throws Exception {
      openMocks.close();
    }

    @Test
    void testCadastrar() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoPadrao.createProduto());

        assertDoesNotThrow(()-> produtoService.cadastrar(produtoDtoTeste));
    }

    @Test
    void testAlterar() {
        when(produtoRepository.findById(anyLong())).thenReturn(produtoTeste);       
        
        assertDoesNotThrow(()-> produtoService.alterar(produtoDtoTeste));
    }

    @Test
    void testExcluir() {
        when(produtoRepository.findById(anyLong())).thenReturn(produtoTeste);      
        doNothing().when(produtoRepository).deleteById(anyLong()); 
        
        assertDoesNotThrow(()-> produtoService.excluir(produtoDtoTeste));
    }
}
