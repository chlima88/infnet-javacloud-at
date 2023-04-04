package br.edu.infnet.atapi.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.edu.infnet.atapi.model.Eletrica;

public interface IEletricaRepository extends CrudRepository<Eletrica, Integer>  {

	Eletrica findByCodigo(String documento);
	@Query("from Eletrica e where e.usuario.empresa = :empresa")
	Collection<Eletrica> findAllByEmpresa(String empresa, Sort sort);

	List<Eletrica> findAll(Sort sort);
}
