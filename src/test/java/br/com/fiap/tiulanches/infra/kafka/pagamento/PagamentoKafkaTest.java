package br.com.fiap.tiulanches.infra.kafka.pagamento;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.controller.NotificacaoController;
import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.message.pagamento.PagamentoEvent;
import br.com.fiap.tiulanches.core.enums.Pago;

class PagamentoKafkaTest {

    @Mock
    private PedidoController pedidoController;

    @Mock
    private NotificacaoController notificacaoController;

    private AutoCloseable openMocks;    

    @BeforeEach
    void beforeEach(){        
        openMocks = MockitoAnnotations.openMocks(this);        

        doNothing().when(notificacaoController).notificar(anyLong(), anyString());
    }

    @AfterEach
    void afterEach() throws Exception {
        openMocks.close();
    }

    @Test
    void testprocessaPagamentoMensagem() {
        PagamentoKafka pagamentoKafka;
        pagamentoKafka = new PagamentoKafka(pedidoController, notificacaoController);

        PagamentoEvent pagamentoEvent = new PagamentoEvent();
        pagamentoEvent.setIdPedido(1L);
        pagamentoEvent.setPago(Pago.NAO);
        
        doNothing().when(pedidoController).cancelaPedidoNaoPago(anyLong());
        assertDoesNotThrow(()->pagamentoKafka.processaPagamentoMensagem(pagamentoEvent));

        pagamentoEvent.setPago(Pago.SIM);
        doNothing().when(pedidoController).preparar(anyLong());
        assertDoesNotThrow(()->pagamentoKafka.processaPagamentoMensagem(pagamentoEvent));
    }
}
