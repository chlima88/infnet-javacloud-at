package br.edu.infnet.atapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "API de Serviços Automotivos", 
				description = "A API apresentada foi desenvolvida para permitir a criação, leitura, atualização e exclusão de usuários, clientes, serviços e agendamentos, de modo que um usuário possa gerenciar os agendamentos de serviços automotivos aos quais está associado."
					+ "<br><br>Esse projeto foi desenvolvido:"
					+ "<ul><li>pelo aluno: <strong>Charles Costa</strong></li>"
					+ "<li>na disciplina: <strong>Desenvolvimento de Serviços em Nuvem com Java</strong></li>"
					+ "<li>com o <strong>prof. Rubens Lopes Oliveira</strong></li>"
					+ "<li>do curso <strong>Engenharia de Software</strong></li>"
					+ "<li>no <strong>Infnet</strong></li></ul>",
				version = "1.0"),
				servers = @Server(url = "http://at.javacloud.chlima.com", description = "Endereço do Projeto")
		
		)				
public class AtapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtapiApplication.class, args);
	}

}
