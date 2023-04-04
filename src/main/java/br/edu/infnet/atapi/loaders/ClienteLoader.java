package br.edu.infnet.atapi.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.edu.infnet.atapi.model.Cliente;
import br.edu.infnet.atapi.model.Usuario;
import br.edu.infnet.atapi.services.ClienteService;

@Order(2)
@Component
public class ClienteLoader implements ApplicationRunner {

	@Autowired
	ClienteService clienteService;

	@Value("classpath:data/loadCliente.txt")
	Resource resourceFile;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		try {

			try {
				InputStreamReader fileReader = new InputStreamReader(resourceFile.getInputStream());
				BufferedReader file = new BufferedReader(fileReader);

				String linha = file.readLine();
				String[] dados = null;

				while (linha != null) {

					dados = linha.split(";");

					Usuario usuario = new Usuario();
					usuario.setId(Integer.valueOf(dados[3]));

					Cliente cliente = new Cliente(dados[0], dados[1], dados[2]);
					cliente.setUsuario(usuario);
					clienteService.incluir(cliente);

					linha = file.readLine();
				}

				file.close();
				fileReader.close();
			} catch (IOException e) {
				System.out.println("[Erro] DataLoader Cliente - " + e.getMessage());
			}
		} finally {
			System.out.println("[Sucesso] DataLoader Cliente");
		}
	}

}
