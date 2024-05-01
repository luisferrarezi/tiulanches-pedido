package br.com.fiap.tiulanches.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.core.exception.ExceptionErros;
import br.com.fiap.tiulanches.utils.Utils;
import br.com.fiap.tiulanches.utils.painelPedido.PainelPedidoPadrao;
import br.com.fiap.tiulanches.utils.pedido.PedidoEnum;
import br.com.fiap.tiulanches.utils.pedido.PedidoPadrao;

class PedidoApiTest {
    private MockMvc mockMvc;    
    private PainelPedidoPadrao painelPedidoPadrao;
    private PedidoDto pedidoDto;
    private Utils utils;

    private final Long idPedidoPadrao = (Long) PedidoEnum.ID_PEDIDO.getValor();

    @Mock
    PedidoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void beforeEach(){
        PedidoPadrao pedidoPadrao;
        utils = new Utils();
        pedidoPadrao = new PedidoPadrao();
        painelPedidoPadrao = new PainelPedidoPadrao();
        pedidoDto = pedidoPadrao.createPedidoDto();

        openMocks = MockitoAnnotations.openMocks(this);

        PedidoApi api = new PedidoApi(controller);
        
        mockMvc = MockMvcBuilders.standaloneSetup(api)
            .setControllerAdvice(new ExceptionErros())
            .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
            .build();    
    }

    @AfterEach
    void afterEach() throws Exception {
        openMocks.close();
    }    
    
    @Test
    void testConsultarPainelPedidos() throws Exception {
        PainelPedidoDto painelPedidoDto = painelPedidoPadrao.creatPainelPedidoDto();
        List<PainelPedidoDto> listPainelPedido = new ArrayList<>(Collections.singletonList(
            painelPedidoDto
        ));

        when(controller.consultaPainelPedido()).thenReturn(listPainelPedido);
        mockMvc.perform(get("/pedidos/painel")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testConsultar() throws Exception {        
        Page<PedidoDto> page = new PageImpl<>(Collections.singletonList(
            pedidoDto
        ));

        when(controller.consultaPaginada(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/pedidos")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testConsultarByStatus() throws Exception {        
        List<PedidoDto> listPedido = new ArrayList<>(Collections.singletonList(
            pedidoDto
        ));

        when(controller.consultaByStatus(any(StatusPedido.class))).thenReturn(listPedido);
        mockMvc.perform(get("/pedidos/status/{status}", StatusPedido.RECEBIDO)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDetalhar() throws Exception {
        when(controller.detalhar(anyLong())).thenReturn(pedidoDto);
        mockMvc.perform(get("/pedidos/{id}", idPedidoPadrao)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(idPedidoPadrao));
    }    

    @Test
    void testCadastrar() throws Exception {
        when(controller.cadastrar(any(PedidoDto.class))).thenAnswer(i -> i.getArgument(0));
        when(controller.detalhar(anyLong())).thenReturn(pedidoDto);

        mockMvc.perform(post("/pedidos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(utils.asJsonString(pedidoDto)))
                .andExpect(status().isCreated());
    }

}
