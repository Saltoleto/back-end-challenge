package br.com.me.modelo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatusType {
    APROVADO,
    REPROVADO,
    APROVADO_VALOR_A_MENOR,
    APROVADO_VALOR_A_MAIOR,
    APROVADO_QTD_A_MAIOR,
    APROVADO_QTD_A_MENOR,
    CODIGO_PEDIDO_INVALIDO;
}
