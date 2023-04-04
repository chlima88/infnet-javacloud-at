package br.edu.infnet.atapi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.atapi.dtos.AgendamentoDto;
import br.edu.infnet.atapi.model.Agendamento;
import br.edu.infnet.atapi.model.Servico;
import br.edu.infnet.atapi.repositories.IAgendamentoRepository;

@Service
public class AgendamentoService {
	
	@Autowired
	private IAgendamentoRepository agendamentoRepository;
	@Autowired
	private ServicoService<Servico> servicoService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ClienteService clienteService;

	public Agendamento incluir(Agendamento agendamento) throws Exception {
		
		validar(agendamento);		
		return agendamentoRepository.save(agendamento);
	};
	
	public Agendamento buscar(Integer id) throws Exception {
		return agendamentoRepository.findById(id).get();		
	};
	
	public Collection<Agendamento> obterLista() {
		return agendamentoRepository.findAll();
	};
	
	public Agendamento atualizar(Agendamento agendamento) throws Exception {
		
		if(agendamentoRepository.findById(agendamento.getId()).isEmpty())
			throw new Exception("Id inválido");
		validar(agendamento);
		
		return agendamentoRepository.save(agendamento);
	};
	
	public Agendamento excluir(Integer id) throws Exception {
		Agendamento agendamento = this.buscar(id);
		agendamentoRepository.delete(agendamento);
		
		return agendamento;
	};
		
	public Agendamento criar(AgendamentoDto agendamentoDto) throws Exception {
		List<Servico> servicos = new ArrayList<Servico>();
		for (Integer id: agendamentoDto.getServicos()) {
			Servico servico = servicoService.buscar(id);
			servicos.add(servico);
		}
		Agendamento agendamento = new Agendamento();
		agendamento.setUsuario(usuarioService.buscar(agendamentoDto.getUsuarioId()));
		agendamento.setCliente(clienteService.buscar(agendamentoDto.getClienteId()));
		agendamento.setServicos(servicos);
		agendamento.setData(agendamentoDto.getData());
		agendamento.setDuracaoEmMinutos(agendamentoDto.getDuracaoEmMinutos());
		
		return agendamento;
	};
	
	public void validar(Agendamento agendamento) throws Exception {
		Agendamento agendamentoUsuario = agendamentoRepository.findByDataAndUsuarioId(agendamento.getData(), agendamento.getUsuario().getId());
		if (agendamentoUsuario != null &&  agendamentoUsuario.getUsuario().getId() == agendamento.getUsuario().getId()) {
			throw new Exception("UsuarioId " + agendamentoUsuario.getUsuario().getId() + " já possui agendamento em " + agendamentoUsuario.getData());
		}
		
		Agendamento agendamentoCliente = agendamentoRepository.findByDataAndClienteId(agendamento.getData(), agendamento.getCliente().getId());
		if (agendamentoCliente != null && agendamentoCliente.getCliente().getId() == agendamento.getCliente().getId()) { 
			throw new Exception("ClienteId " + agendamentoCliente.getCliente().getId() + " já possui agendamento em " + agendamentoCliente.getData());
		}		
	}

}
