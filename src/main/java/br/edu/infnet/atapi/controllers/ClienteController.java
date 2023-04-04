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

import br.edu.infnet.atapi.dtos.ClienteDto;
import br.edu.infnet.atapi.model.Cliente;
import br.edu.infnet.atapi.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "clientes")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
			
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Criar cliente", 
		description = "Cria e retorna o objeto do tipo Cliente")
	public Cliente incluir(
			@Parameter(description="Cliente a ser criado. Não pode ser null ou vazio.")
			@RequestBody @Valid ClienteDto clientedto ) {
		
		try {

			Cliente cliente = new Cliente();
			BeanUtils.copyProperties(clientedto, cliente);
			return clienteService.incluir(cliente);
		}  catch (Exception error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
		
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Buscar cliente por id", 
			description = "Retorna o objeto do tipo Cliente associado ao id informado")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public Cliente buscar(
			@Parameter(description="id do cliente") 
			@PathVariable("id") Integer id ) {
		
		try {
			return clienteService.buscar(id);
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
			summary = "Listar todos os clientes",
			description = "Retorna uma lista com todos objetos do tipo Cliente")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public Collection<Cliente> listar() {
		
		try {
			return clienteService.obterLista();
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
			summary = "Atualizar cliente por id",
			description = "Atualiza o objeto do tipo Cliente associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void atualizar( 
				@Parameter(description = "id do cliente") 
				@PathVariable("id") Integer id,
				@Parameter(description = "Cliente a ser atualizado. Não pode ser null ou vazio.")
				@RequestBody @Valid ClienteDto clientedto ) {
			
		try {
			Cliente cliente = new Cliente();
			BeanUtils.copyProperties(clientedto, cliente);
			cliente.setId(id);
			clienteService.atualizar(cliente);	 
		} catch (Exception error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Excluir cliente por id",
			description = "Excluir o objeto do tipo Cliente associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void excluir(
				@Parameter(description="id do cliente")
				@PathVariable("id") Integer id ) {
		
		try {
			clienteService.excluir(id);
		} catch(DataIntegrityViolationException error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Não foi possível excluir: O cliente está associado a um agendamento.",
					error);
		} catch (Exception error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
}
