package br.com.fiap.tiulanches.bdd;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.tiulanches.adapter.message.pedido.PedidoMessage;
import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteRepository;
import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoRepository;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoRepository;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoRepository;
import br.com.fiap.tiulanches.core.entity.cliente.Cliente;
import br.com.fiap.tiulanches.core.entity.produto.Produto;
import br.com.fiap.tiulanches.core.enums.Logado;
import br.com.fiap.tiulanches.core.service.PedidoService;
import br.com.fiap.tiulanches.utils.cliente.ClientePadrao;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;
import br.com.fiap.tiulanches.utils.produto.ProdutoPadrao;

class PedidoServiceBDDTest {
    private PedidoService pedidoService;
    private PedidoDto pedidoDtoTeste;
    private PedidoPadrao pedidoPadrao;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PainelPedidoRepository painelPedidoRepository;

    @Mock
    private PedidoMessage pedidoMessage;

    AutoCloseable openMocks;

    @BeforeEach
    void beforeEach(){
        openMocks = MockitoAnnotations.openMocks(this);
        
        pedidoPadrao = new PedidoPadrao();
        pedidoDtoTeste = pedidoPadrao.createPedidoDto();
        pedidoService = new PedidoService(pedidoRepository, produtoRepository, clienteRepository, painelPedidoRepository, pedidoMessage);
    }

    @AfterEach
    void afterEach() throws Exception {
      openMocks.close();
    }

    @Test
    void testCadastrar() {
        ClientePadrao clientePadrao = new ClientePadrao();
        Optional<Cliente> clienteTeste = Optional.ofNullable(clientePadrao.createClient());

        ProdutoPadrao produtoPadrao = new ProdutoPadrao();
        Optional<Produto> produtoTeste = Optional.ofNullable(produtoPadrao.createProduto());
        given(produtoRepository.findById(anyLong())).willReturn(produtoTeste);

        clienteTeste.get().setLogado(Logado.SIM);
        given(clienteRepository.findById(anyString())).willReturn(clienteTeste);

        assertDoesNotThrow(()-> pedidoService.cadastrar(pedidoDtoTeste));
    }
}
