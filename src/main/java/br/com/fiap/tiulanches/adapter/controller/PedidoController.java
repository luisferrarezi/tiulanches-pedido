package br.com.fiap.tiulanches.adapter.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.core.enums.StatusPedido;

import java.util.List;

public interface PedidoController {	
	public Page<PedidoDto> consultaPaginada(Pageable paginacao);
	public List<PedidoDto> consultaByStatus(StatusPedido status);
	public PedidoDto detalhar(Long id);	
	public PedidoDto cadastrar(PedidoDto dto);
	public List<PainelPedidoDto> consultaPainelPedido();
	public void atualizaStatus(PedidoDto dto);
	public void preparar(Long id);
}
