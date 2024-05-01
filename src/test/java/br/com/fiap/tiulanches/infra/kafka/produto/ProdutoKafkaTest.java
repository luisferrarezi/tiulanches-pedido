package br.com.fiap.tiulanches.infra.kafka.produto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.controller.ProdutoController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.produto.ProdutoEvent;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoDto;
import br.com.fiap.tiulanches.utils.produto.ProdutoPadrao;

class ProdutoKafkaTest {

    @Mock
    private ProdutoController controller;
    private AutoCloseable openMocks;

    @BeforeEach
    void beforeEach(){        
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        openMocks.close();
    }

    @Test
    void testProcessaMensagem(){
        ProdutoKafka produtoKafka;
        produtoKafka = new ProdutoKafka(controller);

        ProdutoEvent produtoEvent = new ProdutoEvent();
        produtoEvent.setProdutoDto(new ProdutoPadrao().createProdutoDto());
        
        produtoEvent.setEvento(EventoEnum.CREATE);
        doNothing().when(controller).cadastrar(any(ProdutoDto.class));
        assertDoesNotThrow(()->produtoKafka.processaMensagem(produtoEvent));

        produtoEvent.setEvento(EventoEnum.UPDATE);
        doNothing().when(controller).alterar(any(ProdutoDto.class));
        assertDoesNotThrow(()->produtoKafka.processaMensagem(produtoEvent));

        produtoEvent.setEvento(EventoEnum.DELETE);
        doNothing().when(controller).excluir(any(ProdutoDto.class));
        assertDoesNotThrow(()->produtoKafka.processaMensagem(produtoEvent));        
    }
}
