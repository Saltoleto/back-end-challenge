package br.com.me.excessao;


import br.com.me.util.Mensagens;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@ResponseBody
public class ManipuladorExcessaoCustomizado extends ResponseEntityExceptionHandler {

	Mensagens Mensagens;

	public ManipuladorExcessaoCustomizado(Mensagens mensagens) {
		Mensagens = mensagens;
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	private ErroResposta getErrorResponse(String mensage, HttpStatus status, List<Erro> errors) {
		return ErroResposta.builder().mensagem(mensage).codigo(status.value()).status(status.getReasonPhrase())
				.erros(errors).build();
	}

	private List<Erro> getErrors(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new Erro(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
				.collect(Collectors.toList());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> errors = getErrors(ex);
		ErroResposta errorResponse = getErrorResponse(Mensagens.getBadRequest(), status, errors);
		return new ResponseEntity<>(errorResponse, status);
	}

}