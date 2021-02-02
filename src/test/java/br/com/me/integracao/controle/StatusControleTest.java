package br.com.me.integracao.controle;

import br.com.me.MeApplication;
import br.com.me.MockTestes;
import br.com.me.controle.StatusControle;
import br.com.me.modelo.*;
import br.com.me.repositorio.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeApplication.class)
@AutoConfigureMockMvc
class StatusControleTest extends MockTestes {

    @Autowired
    StatusControle statusControle;

    @MockBean
    PedidoRepository pedidoRepository;

    Pedido pedido;
    ItemPedido itemPedido;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        itemPedido = ItemPedido.builder()
                .id(1L)
                .descricao("Item1")
                .qtd(10)
                .precoUnitario(BigDecimal.TEN)
                .build();
        pedido = Pedido.builder()
                .id(1L)
                .itens(Arrays.asList(itemPedido))
                .pedido("123456")
                .build();
        when(pedidoRepository.findByPedido(pedido.getPedido())).thenReturn(Optional.of(pedido));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " status for igual a REPROVADO")
    void reprovado() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.REPROVADO, BigDecimal.TEN, 5, pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.REPROVADO, statusResposta.getStatus().get(0));
    }

    @Test
    @DisplayName("pedido não for localizado no banco de dados.")
    void codigoInvalido() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO, BigDecimal.TEN, 5, "1n");
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.CODIGO_PEDIDO_INVALIDO, statusResposta.getStatus().get(0));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " itensAprovados for igual a quantidade de itens do pedido.\n" +
            " valorAprovado for igual o valor total do pedido.\n" +
            " status for igual a APROVADO.")
    void aprovado() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO, pedido.calculaValorTotalPedido(),
                pedido.calculaQuantidadeTotalItensPedido(), pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO, statusResposta.getStatus().get(0));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " valorAprovado for menor que o valor total do pedido\n" +
            " status for igual a APROVADO")
    void aprovadoValorMenor() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO,
                pedido.calculaValorTotalPedido().subtract(BigDecimal.ONE),
                pedido.calculaQuantidadeTotalItensPedido(), pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO_VALOR_A_MENOR, statusResposta.getStatus().get(0));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " itensAprovados for menor que a quantidade de itens do pedido.\n" +
            " status for igual a APROVADO")
    void aprovadoQtdAmenor() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO,
                pedido.calculaValorTotalPedido(),
                pedido.calculaQuantidadeTotalItensPedido() - 1, pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO_QTD_A_MENOR, statusResposta.getStatus().get(0));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " valorAprovado for maior que o valor total do pedido\n" +
            " status for igual a APROVADO")
    void aprovadoValorAmaior() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO,
                pedido.calculaValorTotalPedido().add(BigDecimal.ONE),
                pedido.calculaQuantidadeTotalItensPedido(), pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO_VALOR_A_MAIOR, statusResposta.getStatus().get(0));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " itensAprovados for maior que a quantidade de itens do pedido.\n" +
            " status for igual a APROVADO")
    void aprovadoQuantidaderAmaior() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO,
                pedido.calculaValorTotalPedido(),
                pedido.calculaQuantidadeTotalItensPedido() + 1, pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO_QTD_A_MAIOR, statusResposta.getStatus().get(0));
    }


    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " valorAprovado for maior que o valor total do pedido\n" +
            " itensAprovados for maior que a quantidade de itens do pedido.\n" +
            " status for igual a APROVADO")
    void aprovadoValorAmaiorQuantidadeAmaior() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO,
                pedido.calculaValorTotalPedido().add(BigDecimal.ONE),
                pedido.calculaQuantidadeTotalItensPedido() + 1, pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO_VALOR_A_MAIOR, statusResposta.getStatus().get(0));
        assertEquals(StatusType.APROVADO_QTD_A_MAIOR, statusResposta.getStatus().get(1));
    }

    @Test
    @DisplayName("pedido for localizado no banco de dados.\n" +
            " valorAprovado for maior que o valor total do pedido\n" +
            " itensAprovados for maior que a quantidade de itens do pedido.\n" +
            " status for igual a APROVADO")
    void aprovadoValorAmenorQuantidadeAmenor() throws Exception {
        StatusPedidoDTO statusPedidoDTO = criaStatusPedido(StatusType.APROVADO,
                pedido.calculaValorTotalPedido().subtract(BigDecimal.ONE),
                pedido.calculaQuantidadeTotalItensPedido() - 1, pedido.getPedido());
        StatusResposta statusResposta = alteraStatus(statusPedidoDTO);
        assertEquals(StatusType.APROVADO_VALOR_A_MENOR, statusResposta.getStatus().get(0));
        assertEquals(StatusType.APROVADO_QTD_A_MENOR, statusResposta.getStatus().get(1));
    }

    private StatusResposta alteraStatus(StatusPedidoDTO statusPedidoDTO) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post(CAMINHO_STATUS)
                .content(objectMapper.writeValueAsString(statusPedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        return objectMapper.readValue(response.getContentAsString(), StatusResposta.class);
    }
}