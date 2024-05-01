package br.com.fiap.tiulanches.utils.pedido;

import java.math.BigDecimal;

public enum ItemPedidoEnum {
    ID_ITEM(1L),
    ID_PEDIDO(1L),
    PRECO_UNITARIO(BigDecimal.valueOf(11.20)),
    QUANTIDADE(2),
    OBSERVACAO("teste");

    private Object valor;

    ItemPedidoEnum(Object valor){
        this.valor = valor;
    }

    public Object getValor(){
        return valor;
    }    
}
