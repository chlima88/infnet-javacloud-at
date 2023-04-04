package br.edu.infnet.atapi.repositories;

import java.util.Collection;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import br.edu.infnet.atapi.model.Cliente;

public interface IClienteRepository extends CrudRepository<Cliente, Integer>  {

	Cliente findByDocumento(String documento);
	
	Collection<Cliente> findAllByUsuarioEmpresa(String empresa);

	Collection<Cliente> findAll(Sort sort);

}
