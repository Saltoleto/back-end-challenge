package br.com.me.servico;

import br.com.me.modelo.Pedido;
import br.com.me.modelo.StatusPedidoDTO;
import br.com.me.modelo.StatusType;

public class StatusAprovadoQuantidadeAmenorChain extends StatusPedidoChain {

    @Override
    public void validaStatus(Pedido pedido, StatusPedidoDTO statusPedidoDTO) {
        if (statusPedidoDTO.getItensAprovados().compareTo(pedido.calculaQuantidadeTotalItensPedido()) < 0
                && StatusType.APROVADO.name().equals(statusPedidoDTO.getStatus())) {
            statusPedidoDTO.addStatus(StatusType.APROVADO_QTD_A_MENOR);
        }
        this.proximoStatus(pedido, statusPedidoDTO);
    }

}