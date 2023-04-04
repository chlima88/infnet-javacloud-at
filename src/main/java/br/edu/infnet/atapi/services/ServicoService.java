package br.edu.infnet.atapi.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.atapi.model.Servico;
import br.edu.infnet.atapi.repositories.IEletricaRepository;
import br.edu.infnet.atapi.repositories.ILanternagemRepository;
import br.edu.infnet.atapi.repositories.IMecanicaRepository;
import br.edu.infnet.atapi.repositories.IServicoRepository;

@Service
public class ServicoService<T extends Servico> {

	@Autowired
	private IServicoRepository<T> servicoRepository;
	@Autowired
	private IEletricaRepository eletricaRepository;
	@Autowired
	private ILanternagemRepository lanternagemRepository;
	@Autowired
	private IMecanicaRepository mecanicaRepository;

	public T salvar(T servico) throws Exception {
		T servicoEncontrado = servicoRepository.findByCodigo(servico.getCodigo());
		if( servicoEncontrado != null && servicoEncontrado.getId() != servico.getId())
			throw new Exception("Codigo já cadastrado");
		
		return servicoRepository.save(servico);
	};
	
	public Collection<T> obterLista() {
		return servicoRepository.findAll();
	};
	
	@SuppressWarnings("unchecked")
	public Collection<T> obterLista(String tipo) throws Exception {
		
		if (tipo.equalsIgnoreCase("eletrica")) {
			return (Collection<T>) eletricaRepository.findAll();
		} else if (tipo.equalsIgnoreCase("lanternagem")) { 
			return (Collection<T>) lanternagemRepository.findAll();
		} else if (tipo.equalsIgnoreCase("mecanica")) {
			return (Collection<T>) mecanicaRepository.findAll();
		} else {
			throw new Exception("Tipo de serico inválido");
		}
		
	};
	
	public T buscar(Integer id) throws Exception {
		return servicoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("servicoId invalido"));
	};

	public T buscarCodigo(String codigo) throws Exception {

		T servico = servicoRepository.findByCodigo(codigo);
		if (servico == null )
			throw new Exception("Codigo " + codigo + " não encontrado");
		return servico;
	}
	
	public void atualizar(T servico) throws Exception {
		
		servicoRepository.findById(servico.getId())
			.orElseThrow(() -> new Exception("Id inválido"));
		
		T servicoEncontrado = servicoRepository.findByCodigo(servico.getCodigo());
		if (servicoEncontrado != null && servicoEncontrado.getId() != servico.getId()) 
			throw new Exception("Codigo ja cadastrado!");
		
		servicoRepository.save(servico);			
	}
	
	public T excluir(Integer id) throws Exception {
		
		Optional<T> servico = servicoRepository.findById(id);
		if (servico.isEmpty()) 
			throw new Exception("Servico não encontrado!");
		servicoRepository.deleteById(id);
		
		return servico.get();
	}

}
