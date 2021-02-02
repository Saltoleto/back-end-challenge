package br.com.me.integracao.controle;

import br.com.me.MockTestes;
import br.com.me.controle.PedidoControle;
import br.com.me.modelo.ItemPedido;
import br.com.me.modelo.Pedido;
import br.com.me.repositorio.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PedidoControleTest extends MockTestes {

    private static final String PEDIDO = "123456";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PedidoControle pedidoControle;

    @Autowired
    PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Lista todos os pedidos")
    void listaTodos() throws Exception {
        mockMvc.perform(get(CAMINHO_PEDIDO))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Busca pedido por id")
    void buscaPorId() throws Exception {
        Pedido pedido = pedidoRepository.save(getPedido(PEDIDO));
        mockMvc.perform(get(CAMINHO_PEDIDO.concat("/{id}"), pedido.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pedido.getId()));
    }

    @Test
    @DisplayName("Salva pedido")
    void salva() throws Exception {
        mockMvc.perform(post(CAMINHO_PEDIDO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPedido(PEDIDO))))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Salva pedido sem numero")
    void salvaPedidoSemNumero() throws Exception {
        mockMvc.perform(post(CAMINHO_PEDIDO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPedido(null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Atualiza pedido")
    void atualiza() throws Exception {
        Pedido pedido = pedidoRepository.save(getPedido(PEDIDO));
        ItemPedido itemPedido = criarItemPedido("Item2", BigDecimal.TEN, 2);
        itemPedido.setId(pedido.getItens().get(0).getId());
        pedido.setItens(Arrays.asList(itemPedido));

        mockMvc.perform(put(CAMINHO_PEDIDO.concat("/{id}"),pedido.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Atualiza pedido inexistente")
    void atualizaPedidoInexistente() throws Exception {
        Pedido pedido = pedidoRepository.save(getPedido(PEDIDO));
        ItemPedido itemPedido = criarItemPedido("Item2", BigDecimal.TEN, 2);
        itemPedido.setId(pedido.getItens().get(0).getId());
        pedido.setItens(Arrays.asList(itemPedido));

        mockMvc.perform(put(CAMINHO_PEDIDO.concat("/{id}"),0l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deleta pedido por id")
    void deleta() throws Exception {
        Pedido pedido = pedidoRepository.save(getPedido(PEDIDO));
        mockMvc.perform(delete(CAMINHO_PEDIDO.concat("/{id}"), pedido.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deleta pedido com id incorreto")
    void deletaPedidoComIdIncorreto() throws Exception {
        Pedido pedido = pedidoRepository.save(getPedido(PEDIDO));
        mockMvc.perform(delete(CAMINHO_PEDIDO.concat("/{id}"), 0l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}