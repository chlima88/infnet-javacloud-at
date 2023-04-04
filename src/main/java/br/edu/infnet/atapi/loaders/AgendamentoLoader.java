package br.edu.infnet.atapi.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.edu.infnet.atapi.model.Agendamento;
import br.edu.infnet.atapi.model.Cliente;
import br.edu.infnet.atapi.model.Eletrica;
import br.edu.infnet.atapi.model.Servico;
import br.edu.infnet.atapi.model.Usuario;
import br.edu.infnet.atapi.services.AgendamentoService;

@Order(4)
@Component
public class AgendamentoLoader implements ApplicationRunner {

	@Autowired
	AgendamentoService agendamentoService;
	
	@Value("classpath:data/loadAgendamento.txt")
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
					usuario.setId(Integer.valueOf(dados[2]));
					
					List<Servico> servicos = new ArrayList<Servico>();
					for (String sid: dados[3].split(" ")) {
						Eletrica servico = new Eletrica();
						servico.setId(Integer.valueOf(sid));
						servicos.add(servico);
					}
					
					Cliente cliente = new Cliente();
					cliente.setId(Integer.valueOf(dados[1]));
					
//					Agendamento agendamento = new Agendamento(cliente, servicos);
					Agendamento agendamento = new Agendamento();
					agendamento.setCliente(cliente);
					agendamento.setServicos(servicos);
					agendamento.setUsuario(usuario);
					agendamento.setData(LocalDateTime.parse(dados[0], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
					agendamento.setDuracaoEmMinutos(60);
					agendamentoService.incluir(agendamento);
					
					linha = file.readLine();
				}
				
				file.close();
				fileReader.close();
			} catch (IOException e) {
				System.out.println("[Erro] DataLoader Agendamento - "+e.getMessage());
			}
		} finally {
			System.out.println("[Sucesso] DataLoader Agendamento");
		}
	}
}
		
