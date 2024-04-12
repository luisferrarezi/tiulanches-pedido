package br.com.fiap.tiulanches.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.tiulanches.adapter.controller.PedidoController;
import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.message.pedido.PedidoMessage;
import br.com.fiap.tiulanches.adapter.repository.cliente.ClienteRepository;
import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanches.adapter.repository.painelpedido.PainelPedidoRepository;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoRepository;
import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoRepository;
import br.com.fiap.tiulanches.core.entitie.cliente.Cliente;
import br.com.fiap.tiulanches.core.entitie.painelpedido.PainelPedido;
import br.com.fiap.tiulanches.core.entitie.pedido.ItemPedido;
import br.com.fiap.tiulanches.core.entitie.pedido.Pedido;
import br.com.fiap.tiulanches.core.entitie.produto.Produto;
import br.com.fiap.tiulanches.core.enums.StatusPedido;
import br.com.fiap.tiulanches.core.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService implements PedidoController {
	private final PedidoRepository pedidoRepository;
	private final ProdutoRepository produtoRepository;
	private final ClienteRepository clienteRepository;
	private final PainelPedidoRepository painelPedidoRepository;
	private final PedidoMessage pedidoMessage;
	
	public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, 
						 ClienteRepository clienteRepository, PainelPedidoRepository painelPedidoRepository,
						 PedidoMessage pedidoMessage) {
		this.clienteRepository = clienteRepository;
		this.pedidoRepository = pedidoRepository;
		this.produtoRepository = produtoRepository;
		this.painelPedidoRepository = painelPedidoRepository;
		this.pedidoMessage = pedidoMessage;
	}
	
	public Page<PedidoDto> consultaPaginada(Pageable paginacao){
		return pedidoRepository.findAll(paginacao).map(PedidoDto::new);
	}

	public List<PedidoDto> consultaByStatus(StatusPedido status){
		List<Pedido> listPedido = pedidoRepository.findByStatus(status);
		
		return listPedido.stream().map(PedidoDto::new).collect(Collectors.toList());
	}	
	
	public PedidoDto detalhar(Long id) {
		Pedido pedido = pedidoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return new PedidoDto(pedido);
    }
	
	public PedidoDto cadastrar(PedidoDto dto){
		Pedido pedido = new Pedido();
		pedido.cadastrar(dto);
		
		if (dto.cliente() != null) {
			Cliente cliente = clienteRepository.findById(dto.cliente().cpf()).orElseThrow(EntityNotFoundException::new);

			if (!cliente.isLogado()){
				throw new BusinessException("Cliente não logado na aplicação!", HttpStatus.UNAUTHORIZED, "Cliente");
			}

			pedido.setCliente(cliente);
		}		
		
		ObjectMapper mapper = new ObjectMapper();
		List<ItemPedido> listItemPedido = mapper.convertValue(dto.listItemPedido(), new TypeReference<List<ItemPedido>>() {});
		
		for (ItemPedido itemPedido : listItemPedido) {
			Produto produto = produtoRepository.findById(itemPedido.getProduto().getIdProduto()).orElseThrow(EntityNotFoundException::new);
			itemPedido.setProduto(produto);
			itemPedido.setPrecoUnitario(produto.getPreco());
			
			pedido.adicionarItem(itemPedido);
		}		
		
		pedidoRepository.save(pedido);		
		PedidoDto pedidoDto = new PedidoDto(pedido);
		pedidoMessage.enviaMensagem(EventoEnum.CREATE, pedidoDto);

		return pedidoDto;
	}

	public List<PainelPedidoDto> consultaPainelPedido() {
		try {
			List<PainelPedido> listPainelPedido = painelPedidoRepository.consultaPainelPedido(StatusPedido.RECEBIDO.ordinal(), 
				 																	      	  StatusPedido.PREPARACAO.ordinal(), 
				 														      	  			  StatusPedido.PRONTO.ordinal());
		
			return listPainelPedido.stream().map(PainelPedidoDto::new).collect(Collectors.toList());
		}
		catch(Exception e) {
			throw new EntityNotFoundException();		
			
		} 
	}

	@Override
	public void atualizaStatus(PedidoDto dto) {
		Pedido pedido = pedidoRepository.findById(dto.idPedido()).orElseThrow(EntityNotFoundException::new);
		pedido.alteraStatus(dto.status());
		pedidoRepository.save(pedido);
	}
}
