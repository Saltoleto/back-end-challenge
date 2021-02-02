package br.com.me.excessao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroResposta implements Serializable {
	private static final long serialVersionUID = 1L;

    private final String mensagem;
    private final int codigo;
    private final String status;
    private final List<Erro> erros;
}