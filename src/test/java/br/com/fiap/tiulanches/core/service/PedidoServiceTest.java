package br.com.fiap.tiulanches.core.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoMessage;
import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteRepository;
import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoRepository;
import br.com.fiap.tiulanches.adapter.repository.pedido.ItemPedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoRepository;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoRepository;
import br.com.fiap.tiulanches.core.entity.cliente.Cliente;
import br.com.fiap.tiulanches.core.entity.painelpedido.PainelPedido;
import br.com.fiap.tiulanches.core.entity.pedido.Pedido;
import br.com.fiap.tiulanches.core.entity.produto.Produto;
import br.com.fiap.tiulanches.core.enums.Logado;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.core.exception.BusinessException;
import br.com.fiap.tiulanches.utils.cliente.ClientePadrao;
import br.com.fiap.tiulanches.utils.pedido.ItemPedidoPadrao;
import br.com.fiap.tiulanches.utils.pedido.PedidoEnum;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;
import br.com.fiap.tiulanches.utils.produto.ProdutoPadrao;

class PedidoServiceTest {
    private PedidoService pedidoService;
    private Optional<Pedido> pedidoTeste;
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
        pedidoTeste = Optional.ofNullable(pedidoPadrao.createPedido());
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

        when(clienteRepository.findById(anyString())).thenReturn(clienteTeste);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoPadrao.createPedido());

        BusinessException exception = assertThrows(BusinessException.class, ()-> pedidoService.cadastrar(pedidoDtoTeste));
        assertEquals("Cliente não logado na aplicação!", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());

        ProdutoPadrao produtoPadrao = new ProdutoPadrao();
        Optional<Produto> produtoTeste = Optional.ofNullable(produtoPadrao.createProduto());
        when(produtoRepository.findById(anyLong())).thenReturn(produtoTeste);

        clienteTeste.get().setLogado(Logado.SIM);
        when(clienteRepository.findById(anyString())).thenReturn(clienteTeste);
        assertDoesNotThrow(()-> pedidoService.cadastrar(pedidoDtoTeste));

        ItemPedidoPadrao itemPedidoPadrao = new ItemPedidoPadrao();
        List<ItemPedidoDto> listItemPedidoDto = new ArrayList<>();
        listItemPedidoDto.add(itemPedidoPadrao.createItemPedidoDto());

        pedidoDtoTeste = new PedidoDto((Long) PedidoEnum.ID_PEDIDO.getValor(),
                                null,
                                        StatusPedido.RECEBIDO,
                                        listItemPedidoDto);

        doNothing().when(pedidoMessage).enviaMensagem(any(EventoEnum.class), any(PedidoDto.class));
        
        assertDoesNotThrow(()-> pedidoService.cadastrar(pedidoDtoTeste));
    }

    @Test
    void testPreparar() {
        when(pedidoRepository.findById(anyLong())).thenReturn(pedidoTeste);       
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoPadrao.createPedido());
        
        assertDoesNotThrow(()-> pedidoService.preparar((Long) PedidoEnum.ID_PEDIDO.getValor()));
    }    

    @Test
    void testAtualizaStatus() {
        when(pedidoRepository.findById(anyLong())).thenReturn(pedidoTeste);       
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoPadrao.createPedido());
        
        assertDoesNotThrow(()-> pedidoService.atualizaStatus(pedidoDtoTeste));
    }        

    @Test
    void testConsultaPainelPedido() {
        List<PainelPedido> list = new ArrayList<>(Collections.emptyList());

        when(painelPedidoRepository.consultaPainelPedido(anyInt(), anyInt(), anyInt())).thenReturn(list);
        List<PainelPedidoDto> pedidos = pedidoService.consultaPainelPedido();
        assertTrue(pedidos.isEmpty());
    }    

    @Test
    void testConsultaByStatus() {
        List<Pedido> list = new ArrayList<>(Collections.emptyList());

        when(pedidoRepository.findByStatus(any(StatusPedido.class))).thenReturn(list);
        List<PedidoDto> pedidos = pedidoService.consultaByStatus(StatusPedido.RECEBIDO);
        assertTrue(pedidos.isEmpty());
    }

    @Test
    void testConsultaPaginada() {
        Page<Pedido> page = new PageImpl<>(Collections.emptyList());

        when(pedidoRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<PedidoDto> pedidos = pedidoService.consultaPaginada(Pageable.unpaged());
        assertTrue(pedidos.isEmpty());
    }

    @Test
    void testDetalhar() {
        when(pedidoRepository.findById(anyLong())).thenReturn(pedidoTeste);
        PedidoDto pedidoDto = pedidoService.detalhar(pedidoTeste.get().getIdPedido());

        assertEquals(pedidoTeste.get().getIdPedido(), pedidoDto.idPedido());
        assertEquals(StatusPedido.RECEBIDO, pedidoDto.status());
        assertEquals(1, pedidoDto.listItemPedido().size());
    }    

    @Test
    void testCancelaPedidoNaoPago() {
        when(pedidoRepository.findById(anyLong())).thenReturn(pedidoTeste);       
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoPadrao.createPedido());
        
        assertDoesNotThrow(()-> pedidoService.cancelaPedidoNaoPago((Long) PedidoEnum.ID_PEDIDO.getValor()));
    }        
}
