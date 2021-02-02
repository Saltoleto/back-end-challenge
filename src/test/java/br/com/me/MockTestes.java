package br.com.me;

import br.com.me.modelo.ItemPedido;
import br.com.me.modelo.Pedido;
import br.com.me.modelo.StatusPedidoDTO;
import br.com.me.modelo.StatusType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockTestes {

    protected static final String CAMINHO_PEDIDO = "/api/pedido";
    protected static final String CAMINHO_STATUS = "/api/status";

    protected Pedido getPedido(String pedido) {
        List<ItemPedido> itensPedidoList = new ArrayList<>();
        itensPedidoList.add(criarItemPedido("Item1", BigDecimal.TEN, 5));
        return criarPedido(pedido, itensPedidoList);
    }


    protected static Pedido criarPedido(String pedido, List<ItemPedido> itemPedidoList) {
        return Pedido.builder().pedido(pedido)
                .itens(itemPedidoList)
                .build();
    }

    protected static ItemPedido criarItemPedido(String descricao, BigDecimal precoUnictario, Integer qtd) {
        return ItemPedido.builder().descricao(descricao)
                .precoUnitario(precoUnictario)
                .qtd(qtd)
                .build();
    }

    protected static StatusPedidoDTO criaStatusPedido(StatusType statusType, BigDecimal valorAprovado, Integer itensAprovados, String pedido){
        return StatusPedidoDTO.builder()
                .status(statusType.name())
                .valorAprovado(valorAprovado)
                .itensAprovados(itensAprovados)
                .pedido(pedido)
                .statusTypes(new ArrayList<>())
                .build();
    }

}