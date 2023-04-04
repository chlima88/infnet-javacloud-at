package br.edu.infnet.atapi.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.infnet.atapi.model.Servico;



public interface IServicoRepository<T extends Servico> extends JpaRepository<T, Integer>  {

	T findByCodigo(String codigo);
	
	Collection<T> findAllByUsuarioEmpresa(String empresa, Sort sort);
		
	List<T> findAll(Sort sort);
}
