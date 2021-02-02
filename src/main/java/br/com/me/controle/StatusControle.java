package br.com.me.controle;


import br.com.me.modelo.*;
import br.com.me.repositorio.PedidoRepository;
import br.com.me.servico.*;
import br.com.me.util.RespostaUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("api/status")
@Validated
public class StatusControle {

    PedidoRepository pedidoRepository;

    public StatusControle(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @ApiOperation(value = "Endpoint para alterar o status do pedido")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Status atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request: Requisição possui campos inválidos")})
    @PostMapping
    public ResponseEntity<StatusResposta> status(@Valid @RequestBody StatusPedidoDTO statusPedidoDTO) {

        Optional<Pedido> pedido = pedidoRepository.findByPedido(statusPedidoDTO.getPedido());

        if (!NumberUtils.isCreatable(statusPedidoDTO.getPedido()) || !pedido.isPresent()){
            return RespostaUtil.resposta(statusPedidoDTO.getPedido(), StatusType.CODIGO_PEDIDO_INVALIDO);
        }

        if (StatusType.REPROVADO.name().equals(statusPedidoDTO.getStatus())) {
            return RespostaUtil.resposta(statusPedidoDTO.getPedido(), StatusType.REPROVADO);
        }

        StatusPedidoChain aprovado = new StatusAprovadoChain();
        aprovado.proximo(new StatusAprovadoValorAmenorChain())
                .proximo(new StatusAprovadoQuantidadeAmenorChain())
                .proximo(new StatusAprovadoValorAmaiorChain())
                .proximo(new StatusAprovadoQuantidadeAmaiorChain());

        aprovado.validaStatus(pedido.get(), statusPedidoDTO);

        return RespostaUtil.resposta(statusPedidoDTO);
    }


}