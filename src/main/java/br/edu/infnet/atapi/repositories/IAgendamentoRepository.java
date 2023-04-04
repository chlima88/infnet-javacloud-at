package br.edu.infnet.atapi.repositories;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.infnet.atapi.model.Agendamento;

public interface IAgendamentoRepository extends JpaRepository<Agendamento, Integer>  {

	Agendamento findByData(LocalDateTime data);
	
	List<Agendamento> findAll(Sort sort);

	Collection<Agendamento> findAllByData(LocalDateTime data, Sort sort);

	Agendamento findByDataAndUsuarioId(LocalDateTime data, Integer id);

	Agendamento findByDataAndClienteId(LocalDateTime data, Integer id);
	
}

