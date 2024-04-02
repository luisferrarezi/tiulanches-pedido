package br.com.fiap.tiulanchespedido.api;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.tiulanchespedido.adapter.controller.PedidoController;
import br.com.fiap.tiulanchespedido.adapter.repository.painelpedido.PainelPedidoDto;
import br.com.fiap.tiulanchespedido.adapter.repository.pedido.PedidoDto;
import br.com.fiap.tiulanchespedido.core.enums.StatusPedido;
import br.com.fiap.tiulanchespedido.infra.swagger.PedidoResponseSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoApi {
	
	private final PedidoController pedidoController;	
	
	public PedidoApi(PedidoController pedidoController){
		this.pedidoController = pedidoController;		
	};
	
	private static Logger logger = LoggerFactory.getLogger(PedidoApi.class);

	@GetMapping(value = "/painel", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista os pedidos com status Recebidos, Em Preparação e Prontos", description = "Lista não paginada os pedidos com status Recebidos, Em Preparação e Prontos", tags = {"Painel Pedido"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso, lista os pedidos com status Recebidos, Em Preparação e Prontos sem paginação"),
			@ApiResponse(responseCode = "404", description = "Falha, pedido não encontrado", content=@Content(schema = @Schema(hidden = true)))
	})			
	public List<PainelPedidoDto> consultarPainelPedidos(){
		logger.info("Consultar pedidos para o painel de pedidos");
		
		return pedidoController.consultaPainelPedido();
	}		

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista todos os pedidos", description = "Retorno paginado, padrão de 10 registros por página", tags = {"Pedido"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso, lista todos os produtos em paginação", content=@Content(schema = @Schema(example = PedidoResponseSwagger.GETBYPAGE)))
	})		
	public ResponseEntity<Page<PedidoDto>> consultar(@ParameterObject @PageableDefault(size=10) Pageable paginacao){
		logger.info("Consultar pedidos");
		
		Page<PedidoDto> page = pedidoController.consultaPaginada(paginacao); 
		
		return ResponseEntity.ok(page);
	}	

	@GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Lista todos os pedidos por status e status de pagamento", description = "Lista não paginada dos pedidos por status e status de pagamento", tags = {"Pedido"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso, lista todos os pedidos por status e status de pagamento sem paginação")
	})			
	public List<PedidoDto> consultarByStatus(@ParameterObject @PathVariable @NotNull
			                                 @Schema(implementation = StatusPedido.class, description = "Status Pedido", example = "RECEBIDO", required = true)
			                                 StatusPedido status){
		logger.info("Consultar pedidos pelo status: " + status.toString());
		
		return pedidoController.consultaByStatus(status);
	}	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retorna dados do pedido", description = "Retorna todos os dados do pedido pelo código", tags = {"Pedido"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description = "Sucesso, retorna dados do pedido", content=@Content(schema = @Schema(example = PedidoResponseSwagger.GETBYID))),
			@ApiResponse(responseCode = "404", description = "Falha, pedido não encontrado", content=@Content(schema = @Schema(hidden = true)))
	})		
	public ResponseEntity<PedidoDto> detalhar(@ParameterObject @PathVariable @NotNull
			                                  @Schema(description = "Código do pedido no sistema", example = "1", required = true)                     
			                                  Long id){
		logger.info("Consultar pedido pelo idPedido: " + id.toString());
		
		PedidoDto pedido = pedidoController.detalhar(id);
		
		return ResponseEntity.ok(pedido);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra o pedido", description = "Cria o pedido e retorna o registro cadastrado", tags = {"Pedido"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucesso, pedido cadastrado", content=@Content(schema = @Schema(example = PedidoResponseSwagger.CREATED))),
			@ApiResponse(responseCode = "400", description = "Falha, não cadastra o pedido falta informação ou está errada",
			             content=@Content(schema = @Schema(example = PedidoResponseSwagger.BADREQUEST))),
		 	@ApiResponse(responseCode = "401", description = "Não autorizado, cliente não está logado na aplicação para fazer o pedido",
			             content=@Content(schema = @Schema(example = PedidoResponseSwagger.UNAUTHORIZED))),						 
			@ApiResponse(responseCode = "404", description = "Falha, produto ou cliente não encontrado", content=@Content(schema = @Schema(hidden = true)))			
	})					
	public ResponseEntity<PedidoDto> cadastrar(@RequestBody @Valid @Schema(example = PedidoResponseSwagger.POST) PedidoDto dto, UriComponentsBuilder uriBuilder){
		logger.info("Cadastrar pedido");
		
		PedidoDto pedido = pedidoController.cadastrar(dto);
		pedido = pedidoController.detalhar(pedido.idPedido());
		
		URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedido.idPedido()).toUri();
		
		return ResponseEntity.created(endereco).body(pedido);
	}
}
