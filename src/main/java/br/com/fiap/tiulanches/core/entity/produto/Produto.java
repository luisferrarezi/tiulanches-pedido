package br.com.fiap.tiulanches.core.entity.produto;

import java.math.BigDecimal;

import br.com.fiap.tiulanches.adapter.repository.produto.ProdutoDto;
import br.com.fiap.tiulanches.core.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Produto")
@Table(name = "PRODUTOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idProduto")
public class Produto {
	@Id
	@Schema(description = "Código do produto após ser criado", example = "1")
	private long idProduto;
	
	@Enumerated(EnumType.ORDINAL)
	@Schema(implementation = Categoria.class, description = "Categoria do produto", example = "LANCHE")	
	private Categoria categoria;
	
	@Size(max=30)
	@Schema(description = "Nome do produto", example = "X-Tudo", maxLength = 30)	
	private String nome;
	
	@Size(max=200)
	@Schema(description = "Descrição do produto", example = "pão, 2 carnes, queijo, presunto, bacon, ovo, alface, tomate, milho e batata.", maxLength = 200)	
	private String descricao;
	
	@NotNull
	@Positive
	@Schema(description = "Preço do produto", example = "19.32")	
	private BigDecimal preco;
	
	@Schema(description = "Tempo em minutos necessário para preparar todo o produto.", example = "35")
	private int tempoPreparo;
	
	@Size(max=400)
	@Schema(description = "Caminho onde a imagem se encontra disponibilizada", 
    		example = "https://img.freepik.com/fotos-gratis/vista-frontal-deliciosas-batatas-fritas-com-cheeseburgers-em-fundo-escuro-lanche-prato-fast-food-torrada-hamburguer-jantar_140725-158687.jpg?w=2000", 
    		maxLength = 400)	
	private String imagem;

	public void cadastrar(ProdutoDto produtoDto) {
		this.idProduto 	  = produtoDto.idProduto();
		this.categoria 	  = produtoDto.categoria();
		this.nome 	   	  = produtoDto.nome();
		this.descricao 	  = produtoDto.descricao();
		this.preco 	      = produtoDto.preco();
		this.tempoPreparo = produtoDto.tempoPreparo();
		this.imagem 	  = produtoDto.imagem();
	}		

	public void atualizar(ProdutoDto produtoDto) {
		this.categoria 	  = produtoDto.categoria();
		this.nome 	   	  = produtoDto.nome();
		this.descricao 	  = produtoDto.descricao();
		this.preco 	      = produtoDto.preco();
		this.tempoPreparo = produtoDto.tempoPreparo();
		this.imagem 	  = produtoDto.imagem();
	}			
}
