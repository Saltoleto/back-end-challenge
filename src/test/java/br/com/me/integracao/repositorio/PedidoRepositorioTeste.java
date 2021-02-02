package br.com.me.integracao.repositorio;

import br.com.me.MockTestes;
import br.com.me.modelo.Pedido;
import br.com.me.repositorio.PedidoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class PedidoRepositorioTeste extends MockTestes {

    private static final String PEDIDO = "123456";
    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Salvar pedido")
    public void salvarPedido() {
        pedidoRepository.save(getPedido(PEDIDO));
        int size = pedidoRepository.findAll().size();
        assertEquals(1, size);
    }

    @Test
    @DisplayName("Obter pedido por id")
    public void obterPedidoPorId() {
        Pedido pedidoSalvo = pedidoRepository.save(getPedido(PEDIDO));
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(pedidoSalvo.getId());
        Assertions.assertThat(pedidoEncontrado.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deletar pedido")
    public void deletarPedido() {
        Pedido pedidoSalvo = pedidoRepository.save(getPedido(PEDIDO));
        pedidoRepository.delete(pedidoSalvo);
        Optional<Pedido> pedidoDeletado = pedidoRepository.findById(pedidoSalvo.getId());
        Assertions.assertThat(pedidoDeletado.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Atualiza pedido")
    public void atualizaPedido() {
        Pedido pedido = pedidoRepository.save(getPedido(PEDIDO));
        pedido.setPedido("12345");
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        assertEquals(pedidoAtualizado.getPedido(), "12345");
    }

}