package br.com.fiap.tiulanches.adapter.message.pagamento;

public interface PagamentoListener {
    public void processaPagamentoMensagem(PagamentoEvent pagamentoEvent);
}
