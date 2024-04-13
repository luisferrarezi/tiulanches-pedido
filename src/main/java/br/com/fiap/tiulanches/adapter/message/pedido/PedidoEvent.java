package br.com.fiap.tiulanches.adapter.message.pedido;

import br.com.fiap.tiulanches.adapter.message.EventoEnum;
import br.com.fiap.tiulanches.adapter.repository.pedido.PedidoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEvent {

    private EventoEnum evento;
    private PedidoDto pedidoDto;
}
