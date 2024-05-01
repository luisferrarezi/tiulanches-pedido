package br.com.fiap.tiulanches.utils.pedido;

public enum PedidoEnum {
    ID_PEDIDO(1L);

    private Object valor;

    PedidoEnum(Object valor){
        this.valor = valor;
    }

    public Object getValor(){
        return valor;
    }    
}
