package br.com.me.servico;

import br.com.me.modelo.Pedido;
import br.com.me.modelo.StatusPedidoDTO;
import br.com.me.modelo.StatusType;

public class StatusAprovadoValorAmenorChain extends StatusPedidoChain {


    @Override
    public void validaStatus(Pedido pedido, StatusPedidoDTO statusPedidoDTO) {
        if (statusPedidoDTO.getValorAprovado().compareTo(pedido.calculaValorTotalPedido()) < 0
                && StatusType.APROVADO.name().equals(statusPedidoDTO.getStatus())) {
            statusPedidoDTO.addStatus(StatusType.APROVADO_VALOR_A_MENOR);
        }
        this.proximoStatus(pedido, statusPedidoDTO);
    }

}