package br.com.fiap.tiulanchespedido.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.tiulanchespedido.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanchespedido.adapter.controller.PedidoController;
import br.com.fiap.tiulanchespedido.core.entitie.cliente.Cliente;
import br.com.fiap.tiulanchespedido.core.entitie.pedido.ItemPedido;
import br.com.fiap.tiulanchespedido.core.entitie.pedido.Pedido;
import br.com.fiap.tiulanchespedido.core.entitie.produto.Produto;
import br.com.fiap.tiulanchespedido.core.enums.Pago;
import br.com.fiap.tiulanchespedido.core.enums.StatusPedido;
import br.com.fiap.tiulanchespedido.core.exception.BusinessException;
import br.com.fiap.tiulanchespedido.adapter.repository.cliente.ClienteRepository;
import br.com.fiap.tiulanchespedido.adapter.repository.pedido.PedidoRepository;
import br.com.fiap.tiulanchespedido.adapter.repository.produto.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService implements PedidoController {
	private final PedidoRepository pedidoRepository;
	private final ProdutoRepository produtoRepository;
	private final ClienteRepository clienteRepository;
	
	public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
		this.pedidoRepository = pedidoRepository;
		this.produtoRepository = produtoRepository;
	}
	
	public Page<PedidoDto> consultaPaginada(Pageable paginacao){
		return pedidoRepository.findAll(paginacao).map(PedidoDto::new);
	}

	public List<PedidoDto> consultaByStatusPago(StatusPedido status, Pago pago){
		List<Pedido> listPedido = pedidoRepository.findByStatusPago(status, pago);
		
		return listPedido.stream().map(pedido -> new PedidoDto(pedido)).collect(Collectors.toList());
	}	
	
	public PedidoDto detalhar(Long id) {
		Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        return new PedidoDto(pedido);
    }
	
	public PedidoDto cadastrar(PedidoDto dto){
		Pedido pedido = new Pedido();
		pedido.cadastrar(dto);
		
		if (dto.cliente() != null) {
			Cliente cliente = clienteRepository.findById(dto.cliente().cpf()).orElseThrow(() -> new EntityNotFoundException());

			if (!cliente.isLogado()){
				throw new BusinessException("Cliente não logado na aplicação!", HttpStatus.UNAUTHORIZED, new String("Cliente"));
			}

			pedido.setCliente(cliente);
		}		
		
		ObjectMapper mapper = new ObjectMapper();
		List<ItemPedido> listItemPedido = mapper.convertValue(dto.listItemPedido(), new TypeReference<List<ItemPedido>>() {});
		
		for (ItemPedido itemPedido : listItemPedido) {
			Produto produto = produtoRepository.findById(itemPedido.getProduto().getIdProduto()).orElseThrow(() -> new EntityNotFoundException());
			itemPedido.setProduto(produto);
			itemPedido.setPrecoUnitario(produto.getPreco());
			
			pedido.adicionarItem(itemPedido);
		}		
		
		pedidoRepository.save(pedido);		
		
		return new PedidoDto(pedido);
	}
}
