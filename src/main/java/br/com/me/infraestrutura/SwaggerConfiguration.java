package br.com.me.infraestrutura;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.me.controle"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mercado Eletrônico - Back-end Challenge")
                .description("Queremos entender como você modela um problema e o transforma em código, como você " +
                        "estrutura, como você testa. Enviar uma aplicação funcionando é o ideal, mas mesmo que não " +
                        "esteja 100 % envie o código para que possamos analisar até onde você chegou.\n" +
                        "O problema que vamos apresentar não tem uma lógica complexa, mas implemente seu código pensando " +
                        "em um sistema extensível e de alta concorrência no uso, é muito importante que você aplique " +
                        "SOLID em tudo que fizer.")
                .version("v1 - 1.0")
                .contact(new Contact("Fernando Saltoleto Fidelis dos Santos",
                        "https://www.linkedin.com/in/fernando-saltoleto-9bb4743b/", "fernandosaltoleto@hotmail.com"))
                .build();
    }
}