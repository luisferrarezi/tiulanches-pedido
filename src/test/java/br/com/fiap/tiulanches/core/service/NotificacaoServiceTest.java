package br.com.fiap.tiulanches.core.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.repository.notificacao.NotificacaoRepository;
import br.com.fiap.tiulanches.core.entity.notificacao.Notificacao;

class NotificacaoServiceTest {

    @Mock
    NotificacaoRepository notificacaoRepository;

    NotificacaoService notificacaoService;

    AutoCloseable openMocks;

    @BeforeEach
    void beforeEach(){
        openMocks = MockitoAnnotations.openMocks(this);

        notificacaoService = new NotificacaoService(notificacaoRepository);

        Notificacao notificacao = new Notificacao();
        notificacao.setIdNotificacao(0);
        notificacao.setIdPedido(0);
        notificacao.setMensagem(null);
        when(notificacaoRepository.save(any(Notificacao.class))).thenReturn(notificacao);
    }

    @AfterEach
    void afterEach() throws Exception {
      openMocks.close();
    }

    @Test
    void testNotificar() {
        
        when(notificacaoRepository.geNotificacaoPedido(anyLong())).thenReturn(0);
        assertDoesNotThrow(()-> notificacaoService.notificar(1L, "teste"));

        when(notificacaoRepository.geNotificacaoPedido(anyLong())).thenReturn(1);
        assertDoesNotThrow(()-> notificacaoService.notificar(1L, "teste"));        
    }
}
