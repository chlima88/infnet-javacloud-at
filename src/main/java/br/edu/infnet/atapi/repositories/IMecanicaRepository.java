package br.edu.infnet.atapi.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.infnet.atapi.model.Mecanica;

public interface IMecanicaRepository extends JpaRepository<Mecanica, Integer>  {

	Mecanica findByCodigo(String codigo);

	Collection<Mecanica> findAllByUsuarioEmpresa(String empresa, Sort sort);

	List<Mecanica> findAll(Sort sort);
	
}
