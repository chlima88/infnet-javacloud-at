package br.edu.infnet.atapi.controllers;

import java.util.Collection;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.infnet.atapi.model.Servico;
import br.edu.infnet.atapi.services.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/servicos")
@Tag(name = "servicos")
public class ServicoController<T extends Servico> {

	@Autowired
	ServicoService<T> servicoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
			summary = "Criar servico", 
			description = "Cria e retorna o objeto do tipo Servico")
	public T incluir(
			@Parameter(description="Servico a ser criado. Não pode ser null ou vazio.")
			@RequestBody T servico ){
		
		try {
			return servicoService.salvar(servico);
		} catch (Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Buscar servico por id", 
			description = "Retorna o objeto do tipo Servico associado ao id informado")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public T buscar(
			@Parameter(description="id do servico") 
			@PathVariable("id") Integer id ) {
		
		try {
			return servicoService.buscar(id);
		} catch (Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}

	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Listar todos os servicos",
			description = "Retorna uma lista com todos objetos do tipo Servico")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public Collection<T> listarPorTipo(@RequestParam(required = false) String tipo ) {
		
		try {
			
			if (tipo != null) {
				return servicoService.obterLista(tipo);	
			}
			return servicoService.obterLista();
		} catch (Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
			
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Atualizar servico por id",
			description = "Atualiza o objeto do tipo Servico associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void atualizar(
			@Parameter(description="id do usuário") 
			@PathVariable("id") Integer id,
			@Parameter(description="Servico a ser atualizado. Não pode ser null ou vazio.")
			@RequestBody T servico ) {
		
		try {
			servico.setId(id);
			servicoService.atualizar(servico);
			
		} catch (Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
			summary = "Excluir servico por id",
			description = "Excluir o objeto do tipo Servico associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void excluir(
			@Parameter(description="id do usuário") 
			@PathVariable("id") Integer id) {

		try {
			servicoService.excluir(id);
		} catch (DataIntegrityViolationException error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Não foi possível excluir: O servico está associado a um agendamento.",
					error);
		} catch (Exception error ) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
}
