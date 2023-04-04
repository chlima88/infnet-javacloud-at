package br.edu.infnet.atapi.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.edu.infnet.atapi.model.Endereco;
import br.edu.infnet.atapi.model.Usuario;
import br.edu.infnet.atapi.services.UsuarioService;

@Order(1)
@Component
public class UsuarioLoader implements ApplicationRunner {

	@Autowired
	UsuarioService usuarioService;
		
	@Value("classpath:data/loadUsuario.txt")
	Resource arquivoUsuarios;	
	@Value("classpath:data/loadEndereco.txt")
	Resource arquivoEnderecos;	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		try {
			
			try {
				InputStreamReader fileReader = new InputStreamReader(arquivoUsuarios.getInputStream());
				BufferedReader file = new BufferedReader(fileReader);
				
				String linha = file.readLine();
				String[] dados = null; 
				
				
				String[] enderecoDados = new BufferedReader(
						new InputStreamReader(arquivoEnderecos.getInputStream())
						).readLine().split(";");
				
				
				while (linha != null) {
					
					dados = linha.split(";");
					
					boolean masterAdmin = Boolean.valueOf(dados[3]);
					List<String> caracteristicas = Arrays.asList(dados[4].split(" "));

					Endereco endereco = new Endereco(
							enderecoDados[0],
							enderecoDados[1],
							enderecoDados[2],
							enderecoDados[3],
							enderecoDados[4]
						);

					Usuario usuario = new Usuario(
							dados[0],
							dados[1],
							dados[2],
							masterAdmin,
							caracteristicas,
							dados[5],
							dados[6]
						);
					usuario.setEndereco(endereco);
					usuario.setImagemUrl(dados[7]);
					usuarioService.incluir(usuario);
					
					linha = file.readLine();
				}
				
				file.close();
				fileReader.close();
			} catch (IOException e) {
				System.out.println("[Erro] DataLoader Usuario - "+e.getMessage());
			}
		} finally {
			System.out.println("[Sucesso] DataLoader Usuario");
		}
	}
}
		
