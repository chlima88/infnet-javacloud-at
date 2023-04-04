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

import br.edu.infnet.atapi.model.Lanternagem;
import br.edu.infnet.atapi.model.Servico;
import br.edu.infnet.atapi.model.Usuario;
import br.edu.infnet.atapi.services.ServicoService;

@Order(3)
@Component
public class LanternagemLoader<T extends Servico> implements ApplicationRunner {
	
	@Autowired
	ServicoService<T> servicoService;
	
	@Value("classpath:data/loadLanternagem.txt")
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
					
					Lanternagem servico = new Lanternagem(
							dados[0], 
							dados[1], 
							Integer.valueOf(dados[2]), 
							Boolean.valueOf(dados[3])
						);
					servico.setTamanhoAvaria(dados[4]);
					
					Usuario usuario = new Usuario();
					usuario.setId(Integer.valueOf(dados[5]));
					servico.setUsuario(usuario);
					
					@SuppressWarnings("unchecked")
					T servicoT = (T) servico; 
					servicoService.salvar(servicoT);
					
					linha = file.readLine();
				}
				
				file.close();
				fileReader.close();
			} catch (IOException e) {
				System.out.println("[Erro] DataLoader Lanternagem - "+e.getMessage());
			}
		} finally {
			System.out.println("[Sucesso] DataLoader Lanternagem");
		}	
	}	
}
