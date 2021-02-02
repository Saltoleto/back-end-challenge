**Requisitos:**

- JDK (Java Development Kit) 1.8 ou superior.
- Apache Maven 3.2.3


**Para baixar as dependencias da aplicação**

- Abra o terminal do seu sistema operacional
- Navegue até a pasta raiz do projeto e digite o seguinte comando:

```
   mvn clean install
   
```


**Como Executar os testes da aplicação**

```
   mvn test
   
```

**Para executar a a aplicação executar o comando abaixo** 

```
  mvn spring-boot:run
  
```

**Para subir o projeto no container docker executar os comandos abaixo** 

Por default foi utilizado a porta 9090, configurada no dockerfile [DockerFile](/src/main/docker/Dockerfile)

```
mvn clean package docker:build
docker run -t -p 9090:9090 --name api-mercado-eletronico fernandosaltoleto/api-mercado-eletronico
  
```
**Observações**

1. Após executar a  aplicação acessar o [link](http://localhost:9090/swagger-ui.html#/)  para conhecer a documentação da API.


Qualquer dúvida fico a disposição.[Clique aqui](https://www.linkedin.com/in/fernando-saltoleto-9bb4743b/)


