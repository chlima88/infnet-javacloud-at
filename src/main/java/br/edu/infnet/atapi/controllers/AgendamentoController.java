package br.edu.infnet.atapi.controllers;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.edu.infnet.atapi.dtos.AgendamentoDto;
import br.edu.infnet.atapi.model.Agendamento;
import br.edu.infnet.atapi.services.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamentos")
@Tag(name = "agendamentos")
public class AgendamentoController {
	
	@Autowired
	AgendamentoService agendamentoService;	
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
			summary = "Buscar agendamento", 
			description = "Retorna o objeto do tipo Agendamento associado ao id informado")
	public Agendamento buscar(
			@Parameter(description = "Agendamento a ser criado. Não pode ser null ou vazio.")
			@PathVariable("id") Integer id) throws Exception {
		try {
			return agendamentoService.buscar(id);			
		} catch (NoSuchElementException error) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"Agendamento não encontrado",
					error);			
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
			summary = "Listar todos os agendamentos",
			description = "Retorna uma lista com todos objetos do tipo Agendamento")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
	public Collection<Agendamento> listar() {
		try {
			return agendamentoService.obterLista();			
		} catch (Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);			
		}
	}
		
			
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
			summary = "Criar agendamento", 
			description = "Cria e retorna o objeto do tipo Agendamento")
	public Agendamento incluir(
			@Parameter(description = "Agendamento a ser criado. Não pode ser null ou vazio.")
			@Valid @RequestBody AgendamentoDto agendamentoDto) {
		
		try {
			Agendamento agendamento = agendamentoService.criar(agendamentoDto);
			return agendamentoService.incluir(agendamento);
			
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
			summary = "Atualizar agendamento por id",
			description = "Atualiza o objeto do tipo Agendamento associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void atualizar(
			@Parameter(description="id do agendamento") 
			@PathVariable("id") Integer id,
			@Parameter(description="Agendamento a ser atualizado. Não pode ser null ou vazio.")
			@Valid @RequestBody AgendamentoDto agendamentoDto
		) {
			
		try {
			Agendamento agendamento = agendamentoService.criar(agendamentoDto);	
			agendamento.setId(id);
			agendamentoService.atualizar(agendamento);	 
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
			summary = "Excluir agendamento por id",
			description = "Excluir o objeto do tipo Agendamento associado ao id informado")
	@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
	public void excluir(
			@Parameter(description="id do usuário") 
			@PathVariable("id") Integer id) {
		
		try {
				agendamentoService.excluir(id);	 
		} catch (Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}

}
