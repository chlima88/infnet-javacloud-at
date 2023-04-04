package br.edu.infnet.atapi.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.atapi.model.Usuario;
import br.edu.infnet.atapi.repositories.IUsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	public Usuario incluir(Usuario usuario) throws Exception {
		
		Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioEncontrado != null)
			throw new Exception("E-mail " + usuario.getEmail() + " ja cadastrado!");
		
		return usuarioRepository.save(usuario);
	}

	public Collection<Usuario> obterLista() {
		return (Collection<Usuario>) usuarioRepository.findAll();
	}
	
	public Usuario buscar(Integer id) {
		try {
			return usuarioRepository.findById(id).get();
		}
		catch (NoSuchElementException error) {
			throw new NoSuchElementException("usuarioId invalido");
		}
	}

	public void atualizar(Usuario usuario) throws Exception {
		
		if(usuarioRepository.findById(usuario.getId()).isEmpty())
			throw new Exception("Id inválido");
		
		Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioEncontrado != null && usuarioEncontrado.getId() != usuario.getId())
			throw new Exception("E-mail " + usuario.getEmail() + " ja cadastrado!");

		usuarioRepository.save(usuario);
	}
	
	public Usuario excluir(Integer id) throws Exception {
		
		Optional<Usuario> servico = usuarioRepository.findById(id);
		if (servico.isEmpty())
			throw new Exception("Usuário não encontrado!");
		usuarioRepository.deleteById(id);
		
		return servico.get();
	}

}
