package br.edu.infnet.atapi.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.infnet.atapi.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>  {


	Usuario findByEmail(String email);
	Usuario findByNome(String email);
	Boolean existsByEmail(String email);
	

	@Query("from Usuario u where u.empresa = :empresa order by u.id")
	Collection<Usuario> findAllByEmpresa(String empresa);
	
	@Query("from Usuario u order by u.empresa asc, u.tipo asc")
	List<Usuario> findAll();

	
}
