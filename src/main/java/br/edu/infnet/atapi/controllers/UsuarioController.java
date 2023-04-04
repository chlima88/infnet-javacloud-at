package br.edu.infnet.atapi.controllers;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.infnet.atapi.dtos.UsuarioDto;
import br.edu.infnet.atapi.model.Usuario;
import br.edu.infnet.atapi.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
			
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
			summary = "Criar usuario", 
			description = "Cria e retorna o objeto do tipo Usuario")
	public Usuario incluir(
			@Parameter(description = "Usuario a ser criado. Não pode ser null ou vazio.")
			@RequestBody @Valid UsuarioDto usuariodto ) {
	
	try {
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(usuariodto, usuario);
		System.out.println(usuariodto + " - " + usuario);
			return usuarioService.incluir(usuario);
		} catch(Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
		
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Buscar usuario por id", 
			description = "Retorna o objeto do tipo Usuario associado ao id informado")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public Usuario buscar(
			@Parameter(description="id do usuário") 
			@PathVariable("id") Integer id ) {
		
		try {
			return usuarioService.buscar(id);
		} catch (NoSuchElementException error ) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"Usuario não encontrado",
					error);
		} catch (Exception error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Listar todos os usuarios",
			description = "Retorna uma lista com todos objetos do tipo Usuario")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public Collection<Usuario>  listar() {
		
		try {
			return usuarioService.obterLista();
		} catch (Exception error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Atualizar usuario por id",
			description = "Atualiza o objeto do tipo Usuario associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void atualizar(
				@Parameter(description="id do usuário") 
				@PathVariable("id") Integer id,
				@Parameter(description="Usuario a ser atualizado. Não pode ser null ou vazio.")
				@RequestBody @Valid UsuarioDto usuariodto ) {
		
		try {
			Usuario usuario = new Usuario();
			BeanUtils.copyProperties(usuariodto, usuario);
			usuario.setId(id);
			usuarioService.atualizar(usuario);
		} catch(Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);			
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Excluir usuario por id",
			description = "Excluir o objeto do tipo Usuario associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void excluir(
				@Parameter(description="id do usuário") 
				@PathVariable("id") Integer id) {
		
		try {
			usuarioService.excluir(id);
		} catch(DataIntegrityViolationException error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Não foi possível excluir: O usuario está associado a um agendamento.",
					error);
		} catch(Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error); 
		}
	}
}

