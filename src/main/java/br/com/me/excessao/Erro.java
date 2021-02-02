package br.com.me.excessao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor

@Getter
public class Erro implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String mensagem;
	private final String campo;
	private final Object parametro;
}