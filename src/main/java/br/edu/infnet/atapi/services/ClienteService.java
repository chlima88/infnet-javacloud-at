package br.edu.infnet.atapi.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.atapi.model.Cliente;
import br.edu.infnet.atapi.repositories.IClienteRepository;

@Service
public class ClienteService {
	
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	public Cliente incluir(Cliente cliente) throws Exception {
		Cliente servicoEncontrado = clienteRepository.findByDocumento(cliente.getDocumento());
		if (servicoEncontrado != null )
			throw new Exception("Documento " + cliente.getDocumento() + " ja cadastrado!");
		
		return clienteRepository.save(cliente);
	}

	public Collection<Cliente> obterLista(){
		return (Collection<Cliente>) clienteRepository.findAll();
	}	
	
	public Cliente buscar(Integer id) throws Exception {
		try {
			return clienteRepository.findById(id).get();
		}
		catch (NoSuchElementException error) {
			throw new NoSuchElementException("clienteId invalido");
		}
	}
	
	public void atualizar(Cliente cliente) throws Exception {
		
		if(clienteRepository.findById(cliente.getId()).isEmpty())
			throw new Exception("Id inválido");
		
		Cliente clienteEncontrado = clienteRepository.findByDocumento(cliente.getDocumento());
		if (clienteEncontrado != null && clienteEncontrado.getId() != cliente.getId())
			throw new Exception("Documento " + cliente.getDocumento() + " ja cadastrado!");

		clienteRepository.save(cliente);
	}	

	public Cliente excluir(Integer key) throws Exception {
		
		Optional<Cliente> cliente = clienteRepository.findById(key);
		if (cliente.isEmpty())
			throw new Exception("Id inválido"); 
		clienteRepository.deleteById(key);
			
		return cliente.get();
	}
	
}
