package br.com.fiap.tiulanchespedido.adapter.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.tiulanchespedido.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanchespedido.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanchespedido.core.enums.StatusPedido;
import java.util.List;

public interface PedidoController {	
	public Page<PedidoDto> consultaPaginada(Pageable paginacao);
	public List<PedidoDto> consultaByStatus(StatusPedido status);
	public PedidoDto detalhar(Long id);	
	public PedidoDto cadastrar(PedidoDto dto);
	public List<PainelPedidoDto> consultaPainelPedido();
}
