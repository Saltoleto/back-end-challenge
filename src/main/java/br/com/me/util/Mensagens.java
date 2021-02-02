package br.com.me.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Mensagens {

	@Value("${mensagem.erro.badRequest}")
	private String badRequest;

}
