package br.com.me.util;

import br.com.me.modelo.StatusPedidoDTO;
import br.com.me.modelo.StatusResposta;
import br.com.me.modelo.StatusType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RespostaUtil {

    public static ResponseEntity<StatusResposta> resposta(String pedido, StatusType statusType) {
        return new ResponseEntity<>(new StatusResposta(pedido, statusType), HttpStatus.OK);
    }

    public static ResponseEntity<StatusResposta> resposta(StatusPedidoDTO statusPedidoDTO) {
        return new ResponseEntity<>(new StatusResposta(statusPedidoDTO.getPedido(), statusPedidoDTO.getStatusTypes()), HttpStatus.OK);
    }
}