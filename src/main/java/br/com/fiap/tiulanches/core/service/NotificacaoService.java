package br.com.fiap.tiulanches.core.service;

import org.springframework.stereotype.Service;

import br.com.fiap.tiulanches.adapter.controller.NotificacaoController;
import br.com.fiap.tiulanches.adapter.repository.notificacao.NotificacaoRepository;
import br.com.fiap.tiulanches.core.entity.notificacao.Notificacao;
import jakarta.transaction.Transactional;

@Service
public class NotificacaoService implements NotificacaoController {

    private NotificacaoRepository notificacaoRepository;

    NotificacaoService(NotificacaoRepository notificacaoRepository){
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    @Transactional
    public void notificar(long idPedido, String mensagem) {
        
        if (notificacaoRepository.geNotificacaoPedido(idPedido) == 0){
            Notificacao notificacao = new Notificacao();
            notificacao.setIdPedido(idPedido);
            notificacao.setMensagem(mensagem);

            notificacaoRepository.save(notificacao);
        }
    }
}
