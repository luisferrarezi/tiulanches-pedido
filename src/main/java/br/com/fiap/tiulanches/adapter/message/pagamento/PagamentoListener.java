package br.com.fiap.tiulanches.adapter.message.pagamento;

import org.springframework.messaging.handler.annotation.Header;

public interface PagamentoListener {
    public void processaStatusMensagem(PagamentoEvent pagamentoEvent, @Header String topic);
}
