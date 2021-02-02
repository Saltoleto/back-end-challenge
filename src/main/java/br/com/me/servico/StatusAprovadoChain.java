package br.com.me.servico;

import br.com.me.modelo.Pedido;
import br.com.me.modelo.StatusPedidoDTO;
import br.com.me.modelo.StatusType;

public class StatusAprovadoChain extends StatusPedidoChain {


    @Override
    public void validaStatus(Pedido pedido, StatusPedidoDTO statusPedidoDTO) {
        if (pedido.calculaQuantidadeTotalItensPedido().compareTo(statusPedidoDTO.getItensAprovados()) == 0
                && pedido.calculaValorTotalPedido().compareTo(statusPedidoDTO.getValorAprovado()) == 0
                && StatusType.APROVADO.name().equals(statusPedidoDTO.getStatus())) {
            statusPedidoDTO.addStatus(StatusType.APROVADO);
        }
        this.proximoStatus(pedido, statusPedidoDTO);
    }

}