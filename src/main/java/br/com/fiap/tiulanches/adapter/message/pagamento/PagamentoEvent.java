package br.com.fiap.tiulanches.adapter.message.pagamento;

import br.com.fiap.tiulanches.core.enums.Pago;
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
public class PagamentoEvent {

    private long idPedido;
    private Pago pago;
}
