package br.edu.infnet.atapi.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.edu.infnet.atapi.model.Lanternagem;

public interface ILanternagemRepository extends CrudRepository<Lanternagem, Integer>  {

	Lanternagem findByCodigo(String codigo);
	@Query("from Lanternagem l where l.usuario.empresa = :empresa ")
	Collection<Lanternagem> findAllByEmpresa(String empresa, Sort sort);

	List<Lanternagem> findAll(Sort sort);
}
