package br.com.me.servico;

import br.com.me.modelo.Pedido;
import br.com.me.modelo.StatusPedidoDTO;


public abstract class  StatusPedidoChain {

    public  StatusPedidoChain next;

    public StatusPedidoChain proximo(StatusPedidoChain next) {
        this.next = next;
        return next;
    }

    public  abstract void validaStatus(Pedido pedido, StatusPedidoDTO statusPedidoDTO);

    public void proximoStatus(Pedido pedidoo, StatusPedidoDTO statusPedidoDTO) {
        if (next != null) {
            next.validaStatus(pedidoo,statusPedidoDTO);
        }
    }

}