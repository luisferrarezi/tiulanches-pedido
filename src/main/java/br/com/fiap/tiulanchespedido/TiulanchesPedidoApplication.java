package br.com.fiap.tiulanchespedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title="API Tiu Lanches - Pedido", version = "24.01.20", description = "Tech Challenge para conclusão Pós Graduação Software Architecture pela FIAP"),
		servers = { @Server(url="http://localhost:8080"), @Server(url="http://localhost:32100") }
)
public class TiulanchesPedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiulanchesPedidoApplication.class, args);
	}

}
